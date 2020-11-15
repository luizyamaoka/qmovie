package br.com.qmovie

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JogoViewModel: ViewModel() {

    val dicasExtrasUtilizadas = MutableLiveData<Int>(0)
    private val MAX_DICAS_EXTRAS = 1

    fun usarDicaExtra() {
        if (dicasExtrasUtilizadas.value!! < MAX_DICAS_EXTRAS)
            dicasExtrasUtilizadas.value = dicasExtrasUtilizadas.value!! + 1
    }

}