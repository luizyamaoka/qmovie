package br.com.qmovie.domain

data class Crew(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    val known_for_department: String,
    val name: String,
    val popularity: Double,
    val profile_path: String,
    val credit_id: String,
    val department: String,
    val job: String
)