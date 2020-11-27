package br.com.qmovie.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchResult<T>(
    val total_results: Int,
    val page: Int,
    val total_pages: Int,
    @SerializedName("results") @Expose val results: ArrayList<T>
)
