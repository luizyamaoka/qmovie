package br.com.qmovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.qmovie.domain.Filme

@Dao
interface FilmeDAO {

    @Insert
    suspend fun addMovie(filme: Filme)

    @Query("SELECT * FROM filme")
    suspend fun getAllMovie() : List<Filme>

    @Query("UPDATE filme SET fav=0 WHERE id=:id")
    suspend fun delFav(id: Int)

    @Query("UPDATE filme SET fav=1 WHERE id=:id")
    suspend fun addFav(id: Int)

    @Query("SELECT * FROM filme WHERE fav=1")
    suspend fun getAllFav() : List<Filme>
}