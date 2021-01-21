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
    val TAG = "LancamentoViewModel"


    fun getUpcoming(){
        viewModelScope.launch(Dispatchers.Main){
            try {
                val response = movieService.getUpcoming()
                listMovies.value = response.results
                response.results.forEach {
                    repoDB.addMovieTask(it)
                }
                //listMovies.value = getAllMoviesDB()
                Log.i("Resultado API : ",listUpcoming.value.toString())
            } catch (e: Exception){
                Log.e("LancamentoViewHolder",e.toString())
            }

        }
    }

    fun getAllMoviesDB() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repoDB.getAllMovieTask()
                listMovies.value = response
            } catch (e: Exception) {
                Log.e("LancamentoViewHolder", e.toString())
            }
        }
    }

    suspend fun getFavMovieDB(): List<Filme> {
        return repoDB.getAllFavTask()
    }


    fun setFavTrue(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                Log.i(TAG, "setFavTrue")
                repoDB.addFavTask(id)
            } catch (e: Exception) {
                Log.e("LancamentoViewHolder", e.toString())
            }
        }
    }

    fun setFavFalse(id: Int){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                Log.i(TAG, "setFavFalse")
                repoDB.delFavTask(id)
            } catch (e: Exception) {
                Log.e("LancamentoViewHolder", e.toString())
            }
        }
    }

}