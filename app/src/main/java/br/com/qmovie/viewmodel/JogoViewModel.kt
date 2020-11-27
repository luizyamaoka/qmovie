package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.TipoDica
import br.com.qmovie.domain.TipoJogo
import br.com.qmovie.service.movieService
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class JogoViewModel(val tipoJogo: TipoJogo): ViewModel() {

    private val MAX_DICAS_EXTRAS = 1
    private lateinit var countdownTimer : CountDownTimer

    var dicasExtrasUtilizadas = 0
    private lateinit var resposta : String

    val respostaEscondida = MutableLiveData<String>()
    val tempoRestante = MutableLiveData<Long>()
    val tempoAcabou = MutableLiveData<Boolean>(false)
    val dicas = MutableLiveData<ArrayList<Dica>>()

    fun usarDicaExtra() {
        if (temDicaExtraDisponivel()) {
            dicasExtrasUtilizadas++
            abrirLetras(2, true)
        }
    }

    fun abrirDica(dica: Dica) {
        if (!dica.estaAberta) {
            dica.estaAberta = true
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

    fun iniciarJogo(tempo: Long = 180000L) {
        val pagina = (1 until 10).random()

        viewModelScope.launch {
            try {
                when (tipoJogo) {
                    TipoJogo.FILME -> {
                        Log.e("JogoViewModel", "Inicio dos servicos ${pagina}")

                        val resultados = movieService.getPopularMovies(page = pagina)
                        val filme = resultados.results.random()
                        resposta = filme.title
                        getDicas(filme)

                        Log.e("JogoViewModel", resposta)
                    }
                }

                criarTimer(tempo)
                esconderNome()
                abrirLetras(1)

            } catch (e: Exception) {
                Log.e("JogoViewModel", e.toString())
            }
        }
    }

    private fun getDicas(filme: Filme) {
        val _dicas = arrayListOf(
            Dica(TipoDica.TEXTO, "O nome original do filme é ${filme.original_title}"),
            Dica(TipoDica.TEXTO, "O filme foi lançado no ano de ${SimpleDateFormat("YYYY").format(filme.release_date)}"),
            Dica(TipoDica.TEXTO, filme.overview)
        )
        _dicas.shuffle()
        dicas.value = _dicas

        Log.e("JogoViewModel", dicas.value.toString())
    }


}