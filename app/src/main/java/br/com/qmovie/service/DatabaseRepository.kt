package br.com.qmovie.service

import android.util.Log
import br.com.qmovie.dao.LancamentoDAO
import br.com.qmovie.domain.Lancamento

interface DatabaseRepository{


    suspend fun addLancamentoTask(lancamento: Lancamento) : List<Lancamento>

    suspend fun getAllLancamentoTask(): List<Lancamento>

    suspend fun getFavLancamentoTask(): List<Lancamento>

    suspend fun delFavTask(id: Int): List<Lancamento>

    suspend fun setFavFalseTask(id: Int): List<Lancamento>

    suspend fun setFavTrueTask(id: Int): List<Lancamento>

}

class RepositoryImplementation(val lancamentoDAO: LancamentoDAO) : DatabaseRepository{

    override suspend fun addLancamentoTask(lancamento: Lancamento): List<Lancamento> {
        lancamentoDAO.addLancamento(lancamento)
        return lancamentoDAO.getAllLancamento()
    }

    override suspend fun getAllLancamentoTask() = lancamentoDAO.getAllLancamento()

    override suspend fun getFavLancamentoTask() = lancamentoDAO.getFavLancamento()

    override suspend fun delFavTask(id: Int): List<Lancamento> {
        Log.i("DB Delete: ", lancamentoDAO.delFav(id).toString())
        return lancamentoDAO.getAllLancamento()
    }

    override suspend fun setFavFalseTask(id: Int): List<Lancamento> {
        lancamentoDAO.setFavFalse(id)
        return lancamentoDAO.getFavLancamento()
    }

    override suspend fun setFavTrueTask(id: Int): List<Lancamento> {
        lancamentoDAO.setFavTrue(id)
        return lancamentoDAO.getFavLancamento()
    }


}