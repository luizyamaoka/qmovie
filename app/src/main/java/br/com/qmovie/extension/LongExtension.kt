package br.com.qmovie.extension

import java.text.NumberFormat
import java.util.*

fun Long.toTime() : String {
    val minutos = (this / 1000) / 60
    val segundos = (this / 1000) % 60

    val segundos_string = if (segundos < 10) "0${segundos}" else "${segundos}"
    val minutos_string = if (minutos < 10) "0${minutos}" else "${minutos}"

    return "$minutos_string:$segundos_string"
}

fun Long.toPoints() = "${NumberFormat.getNumberInstance().format(this)} p"