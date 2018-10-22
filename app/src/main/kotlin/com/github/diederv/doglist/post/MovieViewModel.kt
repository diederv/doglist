package com.github.diederv.doglist.post

import android.arch.lifecycle.MutableLiveData
import com.github.diederv.doglist.base.BaseViewModel
import com.github.diederv.doglist.model.Movie

class MovieViewModel:BaseViewModel() {

    private val id      = MutableLiveData<String>()
    private val title   = MutableLiveData<String>()
    private val year    = MutableLiveData<String>()
    private val type    = MutableLiveData<String>()
    private val poster  = MutableLiveData<String>()

    fun bind(movie: Movie){
        id.value        = movie.id
        title.value     = movie.title
        year.value      = movie.year
        type.value      = movie.type
        poster.value    = movie.poster
    }

    fun getId():MutableLiveData<String>{
        return id
    }

    fun getTitle():MutableLiveData<String>{
        return title
    }

    fun getYear():MutableLiveData<String>{
        return year
    }

    fun getType():MutableLiveData<String>{
        return type
    }

    fun getPoster():MutableLiveData<String>{
        return poster
    }
}