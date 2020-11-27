package br.com.qmovie.domain

data class Cast (
    val id: Int,
    val name: String,
    val original_name: String,
    val character: String,
    val adult: Boolean,
    val gender: Int,
    val known_for_department: String,
    val popularity: Double,
    val profile_path: String,
    val cast_id: Int,
    val credit_id: String
)