package com.github.diederv.doglist.network

import io.reactivex.Observable
import com.github.diederv.doglist.model.MovieSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface MovieApi {

//    @GET("/v2/dog.php?limit=100")
//    fun getDogList(): Observable<>

    @GET("/")
    fun searchAllMoviesFromYear(
            @Query("s") search: String,
            @Query("y") year: String,
            @Query("page") page: Int
    ): Observable<MovieSearchResult>
}