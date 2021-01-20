package br.com.qmovie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.qmovie.dao.LancamentoDAO
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.Lancamento
import br.com.qmovie.extension.Converters

@Database(entities = [Filme::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class AppDB: RoomDatabase(){

    abstract fun lancamentoDAO() : LancamentoDAO

    companion object {
        @Volatile
        private var instance: AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context?) = instance ?: synchronized(LOCK) {
            instance ?: context?.let { buildDatabase(it).also { instance = it } }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDB::class.java, "qmovie.db"
        ).build()
    }

}