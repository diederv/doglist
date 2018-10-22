package com.github.diederv.doglist.post

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.github.diederv.doglist.R
import com.github.diederv.doglist.base.BaseViewModel
import com.github.diederv.doglist.model.MovieSearchResult
import com.github.diederv.doglist.network.MovieApi
import javax.inject.Inject

class MovieListViewModel():BaseViewModel(){

    @Inject
    lateinit var movieApi: MovieApi

    val movieListAdapter: MovieListAdapter = MovieListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private lateinit var subscription: Disposable
    private lateinit var movies: Disposable

    init{
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts(){

        movies = movieApi.searchAllMoviesFromYear(
                        "dog",
                        "2017",
                        1
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveMovieListStart() }
                .doOnTerminate { onRetrieveMovieListFinish() }
                .subscribe(
                        { result -> onRetrieveMovieSearchResultSuccess(result) },
                        { error -> onRetrieveMovieListError(error) }
                )

    }

    private fun onRetrieveMovieListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveMovieListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieListError(error: Throwable){
        errorMessage.value = R.string.post_error
        Log.e(MovieListViewModel::class.simpleName, error.message)
    }

    private fun onRetrieveMovieSearchResultSuccess(result: MovieSearchResult) {
        if (result.search != null) { //result.success()) {
            movieListAdapter.updateMovieList(result.search)
        } else {
            Log.e(MovieListViewModel::class.simpleName, "result:"+ result.search)
        }
    }
}