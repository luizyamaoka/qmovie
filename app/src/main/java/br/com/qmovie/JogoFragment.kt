package br.com.qmovie

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
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.extension.toTime
import br.com.qmovie.service.movieService
import br.com.qmovie.viewmodel.JogoViewModel
import br.com.qmovie.viewmodel.viewModelFactory
import kotlinx.android.synthetic.main.fragment_jogo.*
import kotlinx.android.synthetic.main.fragment_jogo.view.*

class JogoFragment : Fragment() {

    private lateinit var viewModel : JogoViewModel
    private lateinit var tipoJogo: TipoJogo
    private lateinit var adapter: DicaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tipoJogo = (activity as JogoActivity).tipoJogo
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory { JogoViewModel(tipoJogo, movieService) }
        ).get(JogoViewModel::class.java)
        viewModel.iniciarJogo(180000L)
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
            val bundle = bundleOf("tipoMensagem" to "CONFIRMACAO_DESISTIR")
            findNavController().navigate(
                R.id.action_jogoFragment_to_confirmationMessageFragment,
                bundle)
        }
        view.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnAbrirDicaExtra -> {
                    when (viewModel.temDicaExtraDisponivel()) {
                        true -> {
                            val bundle = bundleOf("tipoMensagem" to "CONFIRMACAO_DICA_EXTRA")
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
                    findNavController().navigate(
                        R.id.pontuacaoFragment,
                        bundleOf("tipoJogo" to tipoJogo)
                    )
                }
                false -> {
                    view.etResposta.text = null
                    Toast.makeText(context, "Resposta incorreta", Toast.LENGTH_SHORT).show()
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
            if (it == true) findNavController().navigate(R.id.action_jogoFragment_to_gameOverFragment)
        })

        viewModel.dicas.observe(viewLifecycleOwner, Observer {
            adapter.dicas = it
        })
    }

}

