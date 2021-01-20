package br.com.qmovie.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "lancamento")
class Lancamento(
    @PrimaryKey
    var id: Int,
    var titulo : String,
    var dtLancamento : Date,
    var sinopse: String,
    var favorito: Boolean = false
) : Serializable