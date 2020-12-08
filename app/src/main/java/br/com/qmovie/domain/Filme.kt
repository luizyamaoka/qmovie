package br.com.qmovie.domain

import java.io.Serializable
import java.util.*

data class Filme(
    val id: Int,
    val video: Boolean,
    val vote_average: Double,
    val popularity: Double,
    val vote_count: Int,
    val release_date: Date,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: ArrayList<Int>,
    val overview: String,
    val original_language: String,
    val original_title: String,
    val poster_path: String,
    val title: String) : Serializable