package br.com.qmovie.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*


class Lancamento(
    var id: Int,
    var titulo : String,
    var dtLancamento : Date,
    var sinopse: String,
    var favorito: Boolean = false
) : Serializable