package br.com.qmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.qmovie.activity.JogoActivity
import br.com.qmovie.domain.TipoJogo
import kotlinx.android.synthetic.main.fragment_pontuacao.view.*

class PontuacaoFragment : Fragment() {

    private lateinit var tipoJogo: TipoJogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipoJogo = arguments?.getSerializable("tipoJogo") as TipoJogo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_pontuacao, container,false)
        view.btnCansei.setOnClickListener{
            findNavController().navigate(R.id.action_pontuacaoFragment_to_gameOverFragment)
        }
        view.btnProximaPergunta.setOnClickListener {
            val intent = Intent(activity, JogoActivity::class.java)
            intent.putExtra("tipoJogo", tipoJogo)
            startActivity(intent)
        }
        return view
    }
}