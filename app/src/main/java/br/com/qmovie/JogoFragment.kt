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
import br.com.qmovie.adapter.DicaAdapter
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import br.com.qmovie.extension.toTime
import br.com.qmovie.viewmodel.JogoViewModel
import kotlinx.android.synthetic.main.fragment_jogo.*
import kotlinx.android.synthetic.main.fragment_jogo.view.*

class JogoFragment : Fragment() {

    private lateinit var viewModel : JogoViewModel

    private var dicas = arrayListOf(
        Dica(1, TipoDica.TEXTO, "Filme em que uma aspirante a jornalista se torna assistente de uma revista famosa...", false),
        Dica(2, TipoDica.TEXTO, "Miranda e Andy são os nomes dos protagonistas do filme", false),
        Dica(3, TipoDica.TEXTO, "Lançado em 2006 Direção de David Frankel", false),
        Dica(4, TipoDica.TEXTO, "Outra dica", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(JogoViewModel::class.java)
        viewModel.criaTimer()
        viewModel.esconderNome()
        viewModel.abrirLetras(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jogo, container, false)
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.rvDicas)

        view.rvDicas.adapter = DicaAdapter(viewModel, dicas)

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
            when (viewModel.validaResposta(resposta)) {
                true -> {
                    findNavController().navigate(R.id.action_jogoFragment_to_pontuacaoFragment)
                }
                false -> {
                    view.etResposta.text = null
                    Toast.makeText(context, "Resposta incorreta", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // observa variaveis do mutable live data
        viewModel.dicasExtrasUtilizadas.observe(viewLifecycleOwner, Observer {
            if (viewModel.dicasExtrasUtilizadas.value!! > 0) {
                viewModel.abrirLetras(2, true)
            }
        })
        viewModel.nomeFilmeEscondido.observe(viewLifecycleOwner, Observer {
            view.tvDicaLetras.text = it
        })

        viewModel._tempoRestante.observe(viewLifecycleOwner, Observer {
            view.tvTempoRestante.text = it.toTime()
        })
        viewModel.tempoAcabou.observe(viewLifecycleOwner, Observer {
            if (it == true) findNavController().navigate(R.id.action_jogoFragment_to_gameOverFragment)
        })
        viewModel.filme.observe(viewLifecycleOwner, Observer {
            view.tvDicaLetras.text = it
        })
        viewModel.getFilme(76341)

        return view
    }

}