package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.service.movieService
import kotlinx.coroutines.launch


class JogoViewModel(val tipoJogo: TipoJogo): ViewModel() {

    private val MAX_DICAS_EXTRAS = 1
    private lateinit var countdownTimer : CountDownTimer

    var dicasExtrasUtilizadas = 0
    private lateinit var resposta : String

    val respostaEscondida = MutableLiveData<String>()
    val tempoRestante = MutableLiveData<Long>()
    val tempoAcabou = MutableLiveData<Boolean>(false)

    fun getDicas() = arrayListOf(
        Dica(1, TipoDica.TEXTO, "Filme em que uma aspirante a jornalista se torna assistente de uma revista famosa...", false),
        Dica(2, TipoDica.TEXTO, "Miranda e Andy são os nomes dos protagonistas do filme", false),
        Dica(3, TipoDica.TEXTO, "Lançado em 2006 Direção de David Frankel", false),
        Dica(4, TipoDica.TEXTO, "Outra dica", false)
    )

    fun iniciarJogo(tempo: Long = 180000L) {
        criarTimer(tempo)
        esconderNome()
        abrirLetras(1)
    }

    fun usarDicaExtra() {
        if (temDicaExtraDisponivel()) {
            dicasExtrasUtilizadas++
            abrirLetras(2, true)
        }
    }

    fun abrirDica(dica: Dica) {
        if (!dica.esta_aberta) {
            dica.esta_aberta = true
            adicionarTempo(-10000L)
        }
    }

    fun temDicaExtraDisponivel() = dicasExtrasUtilizadas < MAX_DICAS_EXTRAS

    fun validarResposta(resposta: String) = resposta.toLowerCase() == this.resposta.toLowerCase()

    private fun esconderNome() {
        var nomeEscondido = ""

        resposta.forEach {
            if (it == ' ') nomeEscondido += it
            else nomeEscondido += "*"
        }
        respostaEscondida.value = nomeEscondido
    }

    private fun criarTimer(tempoMillis: Long) {
        countdownTimer = object : CountDownTimer(tempoMillis, 1000L) {
            override fun onFinish() { tempoAcabou.value = true }
            override fun onTick(_tempoRestante: Long) { tempoRestante.value = _tempoRestante }
        }
        countdownTimer.start()
    }

    private fun abrirLetras(quantidadeDesejada: Int, reduzirTempo: Boolean = false) {
        var quantidade = 0
        var nomeArray = respostaEscondida.value!!.toMutableList()
        while (quantidade < quantidadeDesejada) {
            var position = (0 until nomeArray.size).random()
            if (nomeArray[position] == '*') {
                nomeArray[position] = resposta[position]
                quantidade++
            }
        }

        respostaEscondida.value =  nomeArray.joinToString(separator="")

        if (reduzirTempo) adicionarTempo(-10000L)
    }

    private fun adicionarTempo(tempoParaAdicionar: Long) {
        countdownTimer.cancel()

        tempoRestante.value = tempoRestante.value!! + tempoParaAdicionar
        if (tempoRestante.value!! <= 0L) tempoRestante.value = 0

        criarTimer(tempoRestante.value!!)
    }

    fun getResposta() {
        val pagina = (1 until 10).random()

        viewModelScope.launch {
            try {
                when (tipoJogo) {
                    TipoJogo.FILME -> {
                        val resultados = movieService.getPopularMovies(page = pagina)
                        resposta = resultados.results.random().title
                        iniciarJogo()
                        Log.i("JogoViewModel", resposta)
                    }
                }

            } catch (e: Exception) {
                Log.e("JogoViewModel", e.toString())
            }
        }
    }

}