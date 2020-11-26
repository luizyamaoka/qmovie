package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.BuildConfig
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import br.com.qmovie.service.movieService
import kotlinx.coroutines.launch


class JogoViewModel: ViewModel() {

    private val API_KEY: String = BuildConfig.API_KEY
    private val MAX_DICAS_EXTRAS = 1
    private lateinit var countdownTimer : CountDownTimer

    var dicasExtrasUtilizadas = 0
    val nomeFilme : String = "O Diabo veste Prada"

    val nomeFilmeEscondido = MutableLiveData<String>("")
    val _tempoRestante = MutableLiveData<Long>(180000L)
    val tempoAcabou = MutableLiveData<Boolean>(false)

    val filme = MutableLiveData<String>()

    fun iniciaJogo(tempoRestante: Long = 180000L) {
        criaTimer(tempoRestante)
        esconderNome()
        abrirLetras(1)
    }

    fun usarDicaExtra() {
        if (temDicaExtraDisponivel()) {
            dicasExtrasUtilizadas++
            abrirLetras(2, true)
        }
    }

    fun temDicaExtraDisponivel() = dicasExtrasUtilizadas < MAX_DICAS_EXTRAS

    fun esconderNome() {
        var nomeEscondido = ""

        nomeFilme.forEach {
            if (it == ' ') nomeEscondido += it
            else nomeEscondido += "*"
        }
        nomeFilmeEscondido.value = nomeEscondido
    }

    fun abrirLetras(quantidadeDesejada: Int, reduzirTempo: Boolean = false) {
        var quantidade = 0
        var nomeArray = nomeFilmeEscondido.value!!.toMutableList()
        while (quantidade < quantidadeDesejada) {
            var position = (0 until nomeArray.size).random()
            if (nomeArray[position] == '*') {
                nomeArray[position] = nomeFilme.get(position)
                quantidade++
            }
        }

        nomeFilmeEscondido.value =  nomeArray.joinToString(separator="")

        if (reduzirTempo) adicionaTempo(-10000L)
    }

    fun validaResposta(resposta: String) = resposta.toLowerCase() == this.nomeFilme.toLowerCase()

    fun criaTimer(tempoMillis: Long) {
        countdownTimer = object : CountDownTimer(tempoMillis, 1000L) {

            override fun onFinish() {
                tempoAcabou.value = true
            }

            override fun onTick(tempoRestante: Long) {
                _tempoRestante.value = tempoRestante
            }

        }
        countdownTimer.start()
    }

    fun adicionaTempo(tempoParaAdicionar: Long) {
        countdownTimer.cancel()

        _tempoRestante.value = _tempoRestante.value!! + tempoParaAdicionar
        if (_tempoRestante.value!! <= 0L) _tempoRestante.value = 0

        criaTimer(_tempoRestante.value!!)
    }

    fun getFilme(id: Int) {
        viewModelScope.launch {
            try {
                filme.value = movieService.getMovie(id, API_KEY).toString()
            } catch (e: Exception) {
                Log.e("JogoViewModel", e.toString())
            }
        }
    }

    fun getDicas() = arrayListOf(
        Dica(1, TipoDica.TEXTO, "Filme em que uma aspirante a jornalista se torna assistente de uma revista famosa...", false),
        Dica(2, TipoDica.TEXTO, "Miranda e Andy são os nomes dos protagonistas do filme", false),
        Dica(3, TipoDica.TEXTO, "Lançado em 2006 Direção de David Frankel", false),
        Dica(4, TipoDica.TEXTO, "Outra dica", false)
    )

}