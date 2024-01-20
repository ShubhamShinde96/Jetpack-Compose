package com.example.tweetsy.di

import com.example.tweetsy.network.TweetsyApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.jsonbin.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesTweetsyAPI(retrofit: Retrofit): TweetsyApi = retrofit.create(TweetsyApi::class.java)
}