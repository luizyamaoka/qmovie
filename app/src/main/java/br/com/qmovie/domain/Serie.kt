package br.com.qmovie.domain

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Serie (
    val poster_path: String,
    val popularity: Double,
    val id: Int,
    val backdrop_path: String,
    val vote_average: Double,
    val overview: String,
    val first_air_date: Date,
    val origin_country: ArrayList<String>,
    val genre_ids: ArrayList<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String) : Serializable