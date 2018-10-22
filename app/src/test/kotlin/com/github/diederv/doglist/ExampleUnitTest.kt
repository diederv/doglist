package com.github.diederv.doglist

import com.github.diederv.doglist.injection.component.ViewModelInjector
import com.github.diederv.doglist.injection.module.NetworkModule
import com.github.diederv.doglist.injection.module.NetworkTestModule
import com.github.diederv.doglist.model.MovieSearchResult
import com.github.diederv.doglist.network.MovieApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//
//    private val injector: ViewModelInjector = DaggerViewModelInjector
//            .builder()
//            .networkModule(NetworkTestModule)
//            .build()

    @Inject
    lateinit var movieApi: MovieApi


    @Test
    fun test1() {
//        onRetrieveMovieSearchResultSuccess(
//            movieApi.searchAllMoviesFromYear("1", "dogs", "2018", 1).blockingFirst()
//        )
    }

    fun onRetrieveMovieSearchResultSuccess(result : MovieSearchResult) = {
        assertEquals(result.search.size, 3)
        assertEquals(result.search.get(0).id, "1")
        assertEquals(result.search.get(1).id, "2")
        assertEquals(result.search.get(2).id, "3")
    }
}
