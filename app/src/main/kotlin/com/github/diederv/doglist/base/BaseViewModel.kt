package com.github.diederv.doglist.base

import android.arch.lifecycle.ViewModel
import com.github.diederv.doglist.injection.component.DaggerViewModelInjector
import com.github.diederv.doglist.injection.component.ViewModelInjector
import com.github.diederv.doglist.injection.module.NetworkModule
import com.github.diederv.doglist.post.MovieViewModel
import com.github.diederv.doglist.post.MovieListViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MovieListViewModel -> injector.inject(this)
            is MovieViewModel -> injector.inject(this)
        }
    }
}