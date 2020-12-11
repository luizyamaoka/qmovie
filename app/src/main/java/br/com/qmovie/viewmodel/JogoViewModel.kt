package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.R
import br.com.qmovie.domain.*
import br.com.qmovie.service.MovieService
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class JogoViewModel(
    val tipoJogo: TipoJogo,
    val movieService: MovieService
): ViewModel() {

    private val MAX_DICAS_EXTRAS = 1
    private lateinit var countdownTimer : CountDownTimer

    var dicasExtrasUtilizadas = 0
    private lateinit var resposta : String

    val respostaEscondida = MutableLiveData<String>()
    val tempoRestante = MutableLiveData<Long>()
    val tempoAcabou = MutableLiveData<Boolean>(false)
    val dicas = MutableLiveData<ArrayList<Dica>>()
    val perguntaResourceId = MutableLiveData<Int>()
    var totalPoints = 0

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
                        perguntaResourceId.value = R.string.pergunta_filme
                        val resultados = movieService.getPopularMovies(page = pagina)
                        val filme = resultados.results.random()
                        resposta = filme.title
                        Log.i("JogoViewModel", "Resposta: $resposta")
                        val credits = movieService.getCredits(id = filme.id)
                        getDicas(filme, credits)
                    }
                    TipoJogo.SERIE -> {
                        perguntaResourceId.value = R.string.pergunta_serie
                        val tvPopular = movieService.getPopularTv(page = pagina)
                        val serieSorteada = tvPopular.results.random()
                        resposta = serieSorteada.name
                        Log.i("JogoViewModel", "Resposta: $resposta")
                        getDicasSerie(serieSorteada)
                    }
                    TipoJogo.ATOR -> {
                        perguntaResourceId.value = R.string.pergunta_ator

                        val atoresEmAlta = movieService.getPopularActors(page = pagina)
                        var atorSorteado = atoresEmAlta.results.random()
                        var atorDetalhado = movieService.getActor(atorSorteado.id)

                        while (atorDetalhado.known_for_department != "Acting") {
                            atorSorteado = atoresEmAlta.results.random()
                            atorDetalhado = movieService.getActor(atorSorteado.id)
                        }

                        resposta = atorDetalhado.name
                        Log.i("JogoViewModel", "Resposta: $resposta (ID = ${atorDetalhado.id})")

                        getDicasAtor(atorDetalhado, atorSorteado.known_for[0])
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

        _dicas.add(Dica(TipoDica.TEXTO, "O filme foi lançado no ano de ${SimpleDateFormat("yyyy").format(filme.release_date)}"))
        _dicas.add(Dica(TipoDica.TEXTO, filme.overview))

        if (filme.title != filme.original_title)
            _dicas.add(Dica(TipoDica.TEXTO, "O nome original do filme é ${filme.original_title}"))

        if (credits.cast?.size >= 2) {
            _dicas.add(Dica(TipoDica.TEXTO, "${credits.cast[0].name} e ${credits.cast[1].name} atuam neste filme"))
            _dicas.add(Dica(TipoDica.TEXTO, "${credits.cast[0].character} e ${credits.cast[1].character} são 2 personagens do filme"))
        }

        val diretor = credits.crew?.filter { it -> it.job == "Director" }?.first()
        if (diretor != null) _dicas.add(Dica(TipoDica.TEXTO, "${diretor.name} dirigiu o filme"))

        _dicas.shuffle()
        dicas.value = _dicas
    }

    private fun getDicasAtor(ator: Ator, filme: Filme) {
        val _dicas = arrayListOf<Dica>()

        _dicas.add(Dica(TipoDica.TEXTO, "Atuou em \"${filme.title}\""))

        if (ator.gender != 0) {
            if (ator.gender == 1) {
                _dicas.add(Dica(TipoDica.TEXTO, "Gênero Feminino"))
            } else {
                _dicas.add(Dica(TipoDica.TEXTO, "Gênero Masculino"))
            }
        }

        if (ator.birthday != null) {
            _dicas.add(
                Dica(
                    TipoDica.TEXTO,
                    "Nasceu na data de ${SimpleDateFormat("dd/MM/yyyy").format(ator.birthday)}"
                )
            )
        }

        if (ator.deathday != null) {
            _dicas.add(
                Dica(
                    TipoDica.TEXTO,
                    "Faleceu na data de ${SimpleDateFormat("dd/MM/yyyy").format(ator.deathday)}"
                )
            )
        }

        if (ator.place_of_birth != null) {
            _dicas.add(
                Dica(
                    TipoDica.TEXTO,
                    "Natural de ${ator.place_of_birth}"
                )
            )
        }

        _dicas.shuffle()
        dicas.value = _dicas
    }

    private fun getDicasSerie(serie: Serie) {
        val _dicas = arrayListOf<Dica>()

        _dicas.add(Dica(TipoDica.TEXTO, "A série foi lançada em ${SimpleDateFormat("dd/MM/yyyy").format(serie.first_air_date)}"))
        _dicas.add(Dica(TipoDica.TEXTO, serie.overview))
        _dicas.add(Dica(TipoDica.TEXTO, "O país de origem da série é ${serie.origin_country[0]}" ))
        if (serie.name != serie.original_name)
            _dicas.add(Dica(TipoDica.TEXTO, "O nome original da serie é ${serie.original_name}"))

        _dicas.shuffle()
        dicas.value = _dicas
    }

    fun addPoints(point : Int, _totalPoints : Int){
        totalPoints = point + _totalPoints
    }

    fun getPoints(): Int {
        return(totalPoints * 1000)
    }
}