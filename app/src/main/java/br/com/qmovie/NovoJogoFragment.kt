package br.com.qmovie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.qmovie.activity.JogoActivity
import br.com.qmovie.domain.TipoJogo
import kotlinx.android.synthetic.main.fragment_novo_jogo.view.*

class NovoJogoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_novo_jogo, container, false)

        view.newGameFilme.setOnClickListener { iniciarJogo(TipoJogo.FILME) }
        view.newGameSerie.setOnClickListener { iniciarJogo(TipoJogo.SERIE) }
        view.newGameAtor.setOnClickListener { iniciarJogo(TipoJogo.ATOR) }

        return view
    }

    private fun iniciarJogo(tipoJogo: TipoJogo) {
        val intent = Intent(activity, JogoActivity::class.java)
        intent.putExtra("tipoJogo", tipoJogo)
        startActivity(intent)
    }

}