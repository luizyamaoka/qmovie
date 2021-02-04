package br.com.qmovie

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import br.com.qmovie.activity.JogoActivity
import br.com.qmovie.adapter.DicaAdapter
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.Jogo
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.extension.toTime
import br.com.qmovie.service.movieService
import br.com.qmovie.viewmodel.JogoViewModel
import br.com.qmovie.viewmodel.viewModelFactory
import kotlinx.android.synthetic.main.fragment_jogo.*
import kotlinx.android.synthetic.main.fragment_jogo.view.*

class JogoFragment : Fragment() {

    private lateinit var viewModel : JogoViewModel
//    private lateinit var tipoJogo: TipoJogo
    private lateinit var jogo: Jogo
    private lateinit var adapter: DicaAdapter
    private val bgTrack = R.raw.bg_track
    private val wrong = R.raw.wrong
    private val correct = R.raw.correct
    private lateinit var mediaPlayer : MediaPlayer
    private var soundPool: SoundPool? = null
    private val soundWrong = 1
    private val soundCorrect = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        tipoJogo = (activity as JogoActivity).tipoJogo
        jogo = (activity as JogoActivity).jogo!!
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory { JogoViewModel(jogo, movieService) }
        ).get(JogoViewModel::class.java)
        viewModel.iniciarJogo()
        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(context, wrong, 1)
        soundPool!!.load(context, correct, 1)
        mediaPlayer = MediaPlayer.create(context, bgTrack)
        mediaPlayer?.setLooping(true)
        mediaPlayer?.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jogo, container, false)
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.rvDicas)

        adapter = DicaAdapter(viewModel)
        view.rvDicas.adapter = adapter

        definirAcaoBotoes(view)
        observarMutableLiveData(view)

        return view
    }

    fun definirAcaoBotoes(view: View) {
        // Adiciona eventos de cliques nos botoes
        view.toolbar.setNavigationOnClickListener {
            val bundle = bundleOf(
                "tipoMensagem" to "CONFIRMACAO_DESISTIR",
                "jogo" to jogo)
            findNavController().navigate(
                R.id.action_jogoFragment_to_confirmationMessageFragment,
                bundle)
        }
        view.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnAbrirDicaExtra -> {
                    when (viewModel.temDicaExtraDisponivel()) {
                        true -> {
                            val bundle = bundleOf(
                                "tipoMensagem" to "CONFIRMACAO_DICA_EXTRA",
                                "jogo" to jogo)
                            findNavController().navigate(
                                R.id.action_jogoFragment_to_confirmationMessageFragment,
                                bundle)
                        }
                        false -> Toast.makeText(context, "Não há mais dicas extras disponíveis", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }

        view.btnAdivinhar.setOnClickListener {
            val resposta = etResposta.text.toString()
            when (viewModel.validarResposta(resposta)) {
                true -> {
                    soundPool?.play(soundCorrect,1F,1F,0,0,1F)
                    jogo.ganharTempoPeloAcerto()
                    findNavController().navigate(
                        R.id.pontuacaoFragment,
//                        bundleOf("tipoJogo" to tipoJogo)
                        bundleOf("jogo" to viewModel.getJogoAtualizado(true))
                    )
                }
                false -> {
                    view.etResposta.text = null
                    Toast.makeText(context, "Resposta incorreta", Toast.LENGTH_SHORT).show()
                    soundPool?.play(soundWrong,1F,1F,0,0,1F)
                }
            }
        }
    }

    private fun observarMutableLiveData(view: View) {
        viewModel.respostaEscondida.observe(viewLifecycleOwner, Observer {
            view.tvDicaLetras.text = it
        })

        viewModel.tempoRestante.observe(viewLifecycleOwner, Observer {
            view.tvTempoRestante.text = it.toTime()
        })

        viewModel.tempoAcabou.observe(viewLifecycleOwner, Observer {
            if (it == true) findNavController().navigate(
                R.id.action_jogoFragment_to_gameOverFragment,
                bundleOf("jogo" to viewModel.getJogoAtualizado(false)))
        })

        viewModel.dicas.observe(viewLifecycleOwner, Observer {
            adapter.dicas = it
        })

        viewModel.perguntaResourceId.observe(viewLifecycleOwner, Observer {
            tvPergunta.text = getString(it)
        })
    }

    override fun onStop() {
        super.onStop()
        soundPool?.release()
        mediaPlayer?.release()
    }

}

