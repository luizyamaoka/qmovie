package br.com.qmovie.extension

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromDateToString(date: Date): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    @TypeConverter
    fun fromStringToDate(dateStr: String): Date {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return formatter.parse(dateStr)!!
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }
}