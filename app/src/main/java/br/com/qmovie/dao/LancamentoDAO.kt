package br.com.qmovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomWarnings
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.Lancamento

@Dao
interface LancamentoDAO {

    @Insert
    suspend fun addLancamento(filme: Filme)

    @Query("SELECT * FROM favorito")
    suspend fun getAllLancamento(): List<Filme>

    @Query("DELETE FROM favorito WHERE id=:id")
    suspend fun delFav(id: Int)

//    @Query("SELECT * FROM favorito WHERE id=:id")
//    suspend fun findFav(id: Int)

}