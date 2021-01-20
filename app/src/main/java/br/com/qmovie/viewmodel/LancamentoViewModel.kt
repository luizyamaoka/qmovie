package br.com.qmovie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.qmovie.domain.Filme
import br.com.qmovie.service.DatabaseRepository
import br.com.qmovie.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LancamentoViewModel (val movieService: MovieService, val repoDB: DatabaseRepository) : ViewModel() {

    val listUpcoming = MutableLiveData<ArrayList<Filme>>()
    val listFavoritos = MutableLiveData<List<Filme>>()

    fun getUpcoming(){
        viewModelScope.launch(Dispatchers.Main){
            try {
                val response = movieService.getUpcoming()
                listUpcoming.value = response.results
                Log.i("Resultado lançamnetos",listUpcoming.value.toString())

            } catch (e: Exception){
                Log.e("LancamentoViewHolder",e.toString())
            }
        }
    }

    fun addFav(filme : Filme){
        viewModelScope.launch {
            listFavoritos.value = repoDB.addLancamentoTask(filme)
        }
    }

    fun delFav(id : Int){
        viewModelScope.launch {
           repoDB.delFavTask(id)
        }
    }

//    fun findFav(id: Int){
//        viewModelScope.launch {
//            repoDB.findFavTask(id)
//        }
//    }

}