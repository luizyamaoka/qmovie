package br.com.qmovie.domain

import java.io.Serializable

class Dica(
    val id: Int,
    var tipo : TipoDica,
    var conteudo : String,
    var esta_aberta : Boolean = false
) : Serializable {

    override fun toString(): String {
        return "Dica(id=$id, tipo=$tipo, conteudo='$conteudo', esta_aberta=$esta_aberta)"
    }

}
