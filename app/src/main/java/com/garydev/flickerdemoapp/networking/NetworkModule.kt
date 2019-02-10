package com.garydev.flickerdemoapp.networking

import com.garydev.flickerdemoapp.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(private var cacheFile: File) {

    @Provides
    @Singleton
    internal fun provideCall(): Retrofit {
        var cache: Cache? = null
        try {
            cache = Cache(cacheFile, (10 * 1024).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        okHttpClient
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request: Request?
                    request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME)).build()

                    val response = chain.proceed(request!!)
                    response.cacheResponse()
                    response
                }
                .cache(cache)
                .readTimeout(BuildConfig.READTIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(BuildConfig.CONNECTIONTIMEOUT.toLong(), TimeUnit.SECONDS)


        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun providesService(
            networkService: NetworkService): Service {
        return Service(networkService)
    }

    @Provides
    @Singleton
    fun providesNetworkServiceImpl(
            networkService: NetworkService): NetworkServiceImpl {
        return NetworkServiceImpl(networkService)
    }
}