package br.com.qmovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.qmovie.domain.Lancamento

@Dao
interface LancamentoDAO {

    @Insert
    suspend fun addLancamento(lancamento: Lancamento)

    @Query("SELECT * FROM lancamento")
    suspend fun getAllLancamento(): List<Lancamento>

    @Query("SELECT * FROM lancamento WHERE favorito = 'TRUE'")
    suspend fun getFavLancamento(): List<Lancamento>

    @Query("DELETE FROM lancamento WHERE id=:id")
    suspend fun delFav(id: Int)

    @Query("UPDATE lancamento SET favorito = 'FALSE' WHERE ID = :id")
    suspend fun setFavFalse(id: Int)

    @Query("UPDATE lancamento SET favorito = 'TRUE' WHERE ID = :id")
    suspend fun setFavTrue(id: Int)

    //TODO: DELETAR FAVORITO, ATUALIZAR DADOS DO FILME

}