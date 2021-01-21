package br.com.qmovie.service

import androidx.room.Query
import br.com.qmovie.dao.FilmeDAO
import br.com.qmovie.domain.Filme

interface DatabaseRepository {

    suspend fun addMovieTask(filme: Filme) : List<Filme>

    suspend fun getAllMovieTask() : List<Filme>

    suspend fun delFavTask(id: Int) : List<Filme>

    suspend fun addFavTask(id: Int) : List<Filme>

    suspend  fun getAllFavTask() : List<Filme>

}

class RepositoryImplementation(val filmeDAO: FilmeDAO): DatabaseRepository{

    override suspend fun addMovieTask(filme: Filme): List<Filme> {
        filmeDAO.addMovie(filme)
        return filmeDAO.getAllMovie()
    }


    override suspend fun delFavTask(id: Int): List<Filme> {
        filmeDAO.delFav(id)
        return filmeDAO.getAllMovie()
    }

    override suspend fun addFavTask(id: Int): List<Filme> {
        filmeDAO.addFav(id)
        return filmeDAO.getAllMovie()
    }

    override suspend fun getAllMovieTask() = filmeDAO.getAllMovie()

    override suspend fun getAllFavTask() = filmeDAO.getAllFav()

}