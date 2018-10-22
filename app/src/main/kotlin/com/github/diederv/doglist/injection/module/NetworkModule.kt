package com.github.diederv.doglist.injection.module

import com.github.diederv.doglist.BuildConfig
import com.github.diederv.doglist.network.MovieApi
import com.github.diederv.doglist.utils.API_KEY
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import com.github.diederv.doglist.utils.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {

        val client = OkHttpClient().newBuilder()
                .addInterceptor(accessTokenProvidingInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()


        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideMovieApi() : MovieApi {
        return provideRetrofitInterface().create(MovieApi::class.java)
    }

    private fun accessTokenProvidingInterceptor() = Interceptor { chain ->
        val url = chain.request().url().newBuilder().addQueryParameter("apiKey", API_KEY).build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }

}