package br.com.qmovie.domain

import java.io.Serializable
import java.util.*

class Lancamento(
    val id: Int,
    var titulo : String,
    var dtLancamento : Date
) : Serializable {

    override fun toString(): String {
        return "Lancamento(id=$id, titulo=$titulo, Data de Lançamento='$dtLancamento')"
    }

}
