package br.com.qmovie.domain

import java.io.Serializable

class Jogo (
    val tipoJogo: TipoJogo,
    var tempoAnterior: Long,
    var tempoRestante: Long,
    var respostasAcertadas : ArrayList<String>
) : Serializable {

    private val PONTUACAO_POR_RODADA : Int = 1000
    private val TEMPO_INCREMENTAL_POR_ACERTO : Long = 60000L

    var dicasUtilizadas: Int = 0
    var tempoGanhoPeloAcerto: Long = 0


    override fun toString(): String {
        return "Jogo(tipoJogo=$tipoJogo, tempoRestante=$tempoRestante, respostasAcertadas=$respostasAcertadas)"
    }

    fun getTempoUtilizado() : Long = tempoAnterior - tempoRestante

    fun getPontuacaoFinal() = respostasAcertadas.size * PONTUACAO_POR_RODADA

    fun getPontuacaoDaRodada() = PONTUACAO_POR_RODADA

    fun incrementarTempoPorAcerto() {
        tempoRestante += TEMPO_INCREMENTAL_POR_ACERTO
    }

    fun ganharTempoPeloAcerto() {
        tempoGanhoPeloAcerto = TEMPO_INCREMENTAL_POR_ACERTO
    }

    fun getTempoInicialProximaRodada() = tempoRestante + tempoGanhoPeloAcerto
}