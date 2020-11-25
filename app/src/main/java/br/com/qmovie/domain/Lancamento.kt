package br.com.qmovie.domain

import java.io.Serializable
import java.util.*

class Lancamento(
    val id: Int,
    var titulo : String,
    var dtLancamento : Date,
    var diretor: String,
    var genero: String,
    var classificacao: String,
    var sinopse: String,
    var favorito: Boolean = false
) : Serializable