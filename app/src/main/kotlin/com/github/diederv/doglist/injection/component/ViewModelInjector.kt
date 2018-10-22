package com.github.diederv.doglist.injection.component

import dagger.Component
import com.github.diederv.doglist.injection.module.NetworkModule
import com.github.diederv.doglist.post.MovieListViewModel
import com.github.diederv.doglist.post.MovieViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified MovieListViewModel.
     * @param movieListViewModel MovieListViewModel in which to inject the dependencies
     */
    fun inject(movieListViewModel: MovieListViewModel)
    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param postViewModel PostViewModel in which to inject the dependencies
     */
    fun inject(movieViewModel: MovieViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}