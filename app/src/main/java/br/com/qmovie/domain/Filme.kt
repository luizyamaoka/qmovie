package br.com.qmovie.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "favorito")
data class Filme @JvmOverloads constructor (
    @PrimaryKey(autoGenerate = true)
    val PK: Int = 0,
    var id: Int = 0,
    var video: Boolean = true,
    var vote_average: Double = 0.0,
    var popularity: Double = 0.0,
    var vote_count: Int = 0,
    var release_date: Date,
    var adult: Boolean = true,
    var backdrop_path: String = "",
    //@Ignore
    //var genre_ids: ArrayList<Int>,
    var overview: String = "",
    var original_language: String = "",
    var original_title: String = "",
    var poster_path: String = "",
    var title: String = "") : Serializable