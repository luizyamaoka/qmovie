package br.com.qmovie

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import kotlinx.android.synthetic.main.fragment_jogo.*
import kotlinx.android.synthetic.main.fragment_jogo.view.*

class JogoFragment : Fragment() {

    lateinit var countdownTimer : CountDownTimer
    var _tempoRestante: Long = 180000

    private var dicas = arrayListOf(
        Dica(1, TipoDica.TEXTO, "Filme em que uma aspirante a jornalista se torna assistente de uma revista famosa...", false),
        Dica(2, TipoDica.TEXTO, "Miranda e Andy são os nomes dos protagonistas do filme", false),
        Dica(3, TipoDica.TEXTO, "Lançado em 2006 Direção de David Frankel", false),
        Dica(4, TipoDica.TEXTO, "Outra dica", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        criaTimer(_tempoRestante)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jogo, container, false)
        view.rvDicas.adapter = DicaAdapter(this, dicas)
        return view
    }

    fun criaTimer(tempoMillis: Long) {
        countdownTimer = object : CountDownTimer(tempoMillis, 1000L) {

            override fun onFinish() {
//                Navigation.findNavController().navigate(R.id.GameOverFragment)
                // TODO: Vai para tela de fim de jogo
            }

            override fun onTick(tempoRestante: Long) {
                atualizaTempoRestante(tempoRestante)
                _tempoRestante = tempoRestante
            }

        }
        countdownTimer.start()
    }

    private fun atualizaTempoRestante(tempoRestante: Long) {
        val minutos = (tempoRestante / 1000) / 60
        val segundos = (tempoRestante / 1000) % 60

        when {
            segundos < 10 -> tvTempoRestante.text = "$minutos:0$segundos"
            segundos >= 10 -> tvTempoRestante.text = "$minutos:$segundos"
        }
    }

    fun adicionaTempo(tempoParaAdicionar: Long) {
        countdownTimer.cancel()

        _tempoRestante += tempoParaAdicionar
        if (_tempoRestante <= 0) _tempoRestante = 0

        criaTimer(_tempoRestante)
        atualizaTempoRestante(_tempoRestante)

    }
}