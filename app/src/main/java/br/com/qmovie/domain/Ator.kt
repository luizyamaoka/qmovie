package br.com.qmovie.domain

import java.io.Serializable
import java.util.*

data class Ator(
    val id: Int,
    val name: String,
    val birthday: Date?,
    val deathday: Date?,
    val place_of_birth: String?,
    val adult: Boolean,
    val also_known_as: ArrayList<String>,
    val biography: String,
    val gender: Int,
    val known_for_department: String,
    val known_for: ArrayList<Filme>
) : Serializable