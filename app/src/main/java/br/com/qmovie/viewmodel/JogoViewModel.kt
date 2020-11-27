package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.domain.*
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
                        Log.e("JogoViewModel", resultados.toString())
                        val filme = resultados.results.random()
                        Log.e("JogoViewModel", filme.toString())
                        resposta = filme.title
                        Log.e("JogoViewModel", resposta)

                        val credits = movieService.getCredits(id = filme.id)
                        Log.e("JogoViewModel", credits.toString())

                        getDicas(filme, credits)

                        Log.e("JogoViewModel", "getdicas")

                    }
                }

                criarTimer(tempo)
                esconderNome()
                abrirLetras(1)

            } catch (e: Exception) {
                Log.e("JogoViewModel", e.toString())
                Log.e("JogoViewModel", e.stackTrace.toString())
            }
        }
    }

    private fun getDicas(filme: Filme, credits: CreditResult) {
        val _dicas = arrayListOf<Dica>()

        _dicas.add(Dica(TipoDica.TEXTO, "O filme foi lançado no ano de ${SimpleDateFormat("YYYY").format(filme.release_date)}"))
        _dicas.add(Dica(TipoDica.TEXTO, filme.overview))

        Log.e("JogoViewModel.getDicas", "titulo == original_title")
        if (filme.title != filme.original_title)
            _dicas.add(Dica(TipoDica.TEXTO, "O nome original do filme é ${filme.original_title}"))

        Log.e("JogoViewModel.getDicas", "cast")
        if (credits.cast?.size >= 2) {
            _dicas.add(Dica(TipoDica.TEXTO, "${credits.cast[0].name} e ${credits.cast[1].name} atuam neste filme"))
            _dicas.add(Dica(TipoDica.TEXTO, "${credits.cast[0].character} e ${credits.cast[1].character} são 2 personagens do filme"))
        }

        Log.e("JogoViewModel.getDicas", "diretor")
        val diretor = credits.crew?.filter { it -> it.job == "Director" }?.first()
        if (diretor != null) _dicas.add(Dica(TipoDica.TEXTO, "${diretor.name} dirigiu o filme"))

        _dicas.shuffle()
        dicas.value = _dicas

        Log.e("JogoViewModel", dicas.value.toString())
    }


}