package br.com.qmovie.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "filme")
data class Filme(
    @PrimaryKey(autoGenerate = true)
    val PK: Int,
    var id: Int,
    var video: Boolean? ,
    var vote_average: Double? ,
    var popularity: Double? ,
    var vote_count: Int?,
    var release_date: Date,
    var adult: Boolean = false,
    var backdrop_path: String? ,
    //var genre_ids: ArrayList<Int>,
    var overview: String ,
    var original_language: String? ,
    var original_title: String?,
    var poster_path: String?,
    var title: String ,
    var fav: Boolean = false) : Serializable