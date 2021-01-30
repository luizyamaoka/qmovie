package br.com.qmovie.extension

import java.text.NumberFormat

fun Long.toTime() : String {
    val minutos = (this / 1000) / 60
    val segundos = (this / 1000) % 60

    val segundosString = if (segundos < 10) "0${segundos}" else "${segundos}"
    val minutosString = if (minutos < 10) "0${minutos}" else "${minutos}"

    return "$minutosString:$segundosString"
}

fun Long.toPoints() = "${NumberFormat.getNumberInstance().format(this)} p"