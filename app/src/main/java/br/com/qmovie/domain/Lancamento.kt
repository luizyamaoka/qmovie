package br.com.qmovie.domain

import java.io.Serializable
import java.util.*

class Lancamento(
    val id: Int,
    var titulo : String,
    var dtLancamento : Date,
    var favorito: Boolean = false
) : Serializable