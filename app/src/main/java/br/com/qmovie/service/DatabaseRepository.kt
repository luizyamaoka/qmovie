package br.com.qmovie.service

import android.util.Log
import br.com.qmovie.dao.LancamentoDAO
import br.com.qmovie.domain.Filme

interface DatabaseRepository{


    suspend fun addLancamentoTask(filme: Filme) : List<Filme>

    suspend fun getAllLancamentoTask(): List<Filme>

    suspend fun delFavTask(id: Int): List<Filme>

//    suspend fun findFavTask(id: Int): List<Filme>

}

class RepositoryImplementation(val lancamentoDAO: LancamentoDAO) : DatabaseRepository{

    override suspend fun addLancamentoTask(filme: Filme): List<Filme> {
        lancamentoDAO.addLancamento(filme)
        Log.i("Filme add no DB 2",filme.toString())
        return lancamentoDAO.getAllLancamento()
    }

    override suspend fun getAllLancamentoTask() = lancamentoDAO.getAllLancamento()

    override suspend fun delFavTask(id: Int): List<Filme> {
        Log.i("DB Delete: ", lancamentoDAO.delFav(id).toString())
        return lancamentoDAO.getAllLancamento()
    }

//    override suspend fun findFavTask(id: Int): List<Filme> {
//        lancamentoDAO.findFav(id)
//        return lancamentoDAO.getAllLancamento()
//    }

}