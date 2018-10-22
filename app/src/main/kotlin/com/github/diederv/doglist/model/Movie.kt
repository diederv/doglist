package com.github.diederv.doglist.model

import com.google.gson.annotations.SerializedName

class Movie (

        @SerializedName("imdbID")
        val id : String,

        @SerializedName("Title")
        val title : String,

        @SerializedName("Year")
        val year : String,

        @SerializedName("Type")
        val type : String,

        @SerializedName("Poster")
        val poster : String

)