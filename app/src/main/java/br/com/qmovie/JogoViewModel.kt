package br.com.qmovie

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JogoViewModel: ViewModel() {

    private val MAX_DICAS_EXTRAS = 1

    val dicasExtrasUtilizadas = MutableLiveData<Int>(0)
    val nomeFilme : String = "O Diabo veste Prada"
    val nomeFilmeEscondido = MutableLiveData<String>("")

    fun usarDicaExtra() {
        if (temDicaExtraDisponivel())
            dicasExtrasUtilizadas.value = dicasExtrasUtilizadas.value!! + 1
    }

    fun temDicaExtraDisponivel() = dicasExtrasUtilizadas.value!! < MAX_DICAS_EXTRAS

    fun esconderNome() {
        var nomeEscondido = ""

        nomeFilme.forEach {
            if (it == ' ') nomeEscondido += it
            else nomeEscondido += "*"
        }
        nomeFilmeEscondido.value = nomeEscondido
    }

    fun abrirLetras(quantidadeDesejada: Int) {
        var quantidade = 0
        var nomeArray = nomeFilmeEscondido.value!!.toMutableList()
        while (quantidade < quantidadeDesejada) {
            var position = (0 until nomeArray.size).random()
            if (nomeArray[position] == '*') {
                nomeArray[position] = nomeFilme.get(position)
                quantidade++
            }
        }

        nomeFilmeEscondido.value =  nomeArray.joinToString(separator="")
    }

}