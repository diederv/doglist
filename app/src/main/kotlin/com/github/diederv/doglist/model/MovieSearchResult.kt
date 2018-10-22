package com.github.diederv.doglist.model

import com.google.gson.annotations.SerializedName

class MovieSearchResult (

        @SerializedName("imdbID")
        val totalResults: String,

        @SerializedName("Response")
        val response: String,

        @SerializedName("Search")
        val search: List<Movie>
) {
    fun success() = response.toBoolean()
}