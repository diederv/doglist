package com.github.diederv.doglist.injection.module


import com.github.diederv.doglist.model.Movie
import com.github.diederv.doglist.model.MovieSearchResult
import com.github.diederv.doglist.network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Observable
import org.mockito.Mockito
import retrofit2.Retrofit
import org.mockito.Mockito.*
import retrofit2.http.Query

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkTestModule {

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {

        val movieList : ArrayList<Movie> = ArrayList()
        movieList.add(Movie("1", "movie_1", "2018", "movie", ".."))
        movieList.add(Movie("2", "movie_2", "2018", "movie", ".."))
        movieList.add(Movie("3", "movie_3", "2018", "movie", ".."))

        val movieSearchResult = Mockito.mock(MovieSearchResult::class.java)
        Mockito.`when`(movieSearchResult.search).thenReturn(movieList)
        Mockito.`when`(movieSearchResult.response).thenReturn("True")
        Mockito.`when`(movieSearchResult.totalResults).thenReturn("3")

        val retrofit = Mockito.mock(MovieApi::class.java)

        Mockito.`when`(retrofit.searchAllMoviesFromYear(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyInt()
        )).thenReturn(Observable.just(movieSearchResult))

        return retrofit as Retrofit
    }

}