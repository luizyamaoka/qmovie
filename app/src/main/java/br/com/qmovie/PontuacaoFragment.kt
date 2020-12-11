package br.com.qmovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import br.com.qmovie.activity.JogoActivity
import br.com.qmovie.domain.Jogo
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.extension.toTime
import kotlinx.android.synthetic.main.fragment_pontuacao.view.*

class PontuacaoFragment : Fragment() {

    private lateinit var jogo: Jogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jogo = arguments?.getSerializable("jogo") as Jogo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_pontuacao, container,false)
        view.btnCansei.setOnClickListener{
            findNavController().navigate(
                R.id.action_pontuacaoFragment_to_gameOverFragment,
                bundleOf("jogo" to jogo))
        }
        view.btnProximaPergunta.setOnClickListener {
            val intent = Intent(activity, JogoActivity::class.java)
            intent.putExtra("jogo", jogo)
            startActivity(intent)
        }

        view.tvResultadoTempoUtilizado.text = jogo.getTempoUtilizado().toTime()
        view.tvResultadoDicaUtilizada.text = jogo.dicasUtilizadas.toString()
        view.tvResultadoPontos.text = jogo.getPontuacaoDaRodada().toString()

//        jogo.incrementarTempoPorAcerto()
        view.tvTempoTotalResultado.text = jogo.getTempoInicialProximaRodada().toTime()

        return view
    }
}