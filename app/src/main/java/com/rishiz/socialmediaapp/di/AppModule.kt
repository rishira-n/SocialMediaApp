package com.rishiz.socialmediaapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private var retrofit: Retrofit? = null
    private var gson: Gson? = null

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        if (retrofit == null) {
            if (gson == null) {
                gson = GsonBuilder().setLenient().create()
            }

            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Connection", "close")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()

            retrofit = gson?.let { GsonConverterFactory.create(it) }?.let {
                Retrofit.Builder()
                    .baseUrl("http://Techlambda.com:3283/")
                    .client(client)
                    .addConverterFactory(it)
                    .build()
            }

        }
        return retrofit!!
    }
}