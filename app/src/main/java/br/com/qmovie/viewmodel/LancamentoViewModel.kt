package br.com.qmovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.R
import br.com.qmovie.database.AppDB
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.SearchResult
import br.com.qmovie.service.DatabaseRepository
import br.com.qmovie.service.MovieService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LancamentoViewModel (val movieService: MovieService, val repoDB: DatabaseRepository) : ViewModel() {

    val listUpcoming = MutableLiveData<ArrayList<Filme>>()
    val listMovies = MutableLiveData<List<Filme>>()


    fun getUpcoming(){
        viewModelScope.launch(Dispatchers.Main){
            try {
                val response = movieService.getUpcoming()
                listUpcoming.value = response.results
                response.results.forEach {
                    addMovieDatabase(it)
                }
                //listMovies.value = getAllMoviesDB()
                Log.i("Resultado lançamnetos",listUpcoming.value.toString())
            } catch (e: Exception){
                Log.e("LancamentoViewHolder",e.toString())
            }

        }
    }

    suspend fun getAllMoviesDB(): List<Filme> {
        Log.i("getAllMoviesDB",listMovies.value.toString())
        return repoDB.getAllMovieTask()
    }

    suspend fun getFavMoviesDB(): List<Filme> {
        return repoDB.getAllFavTask()
    }

    suspend fun addMovieDatabase(filme: Filme){
        repoDB.addMovieTask(filme)
    }

}