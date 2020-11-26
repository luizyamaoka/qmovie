package br.com.qmovie.domain

class Filme(
    val id: Int,
    val imdb_id: String,
    val title: String) {

    override fun toString(): String {
        return "Filme(id=$id, imdb_id='$imdb_id', title='$title')"
    }
}