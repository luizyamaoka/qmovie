package br.com.qmovie.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreditResult(
    val id: String,
    @SerializedName("cast") @Expose val cast: ArrayList<Cast>,
    @SerializedName("crew") @Expose val crew: ArrayList<Crew>
)