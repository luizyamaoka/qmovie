package br.com.qmovie.domain

import java.io.Serializable

class Dica(
    var tipo : TipoDica,
    var conteudo : String,
    var estaAberta : Boolean = false
) : Serializable {

    override fun toString(): String {
        return "Dica(tipo=$tipo, conteudo='$conteudo', esta_aberta=$estaAberta)"
    }

}
