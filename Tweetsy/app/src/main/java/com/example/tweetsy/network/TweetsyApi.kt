package com.example.tweetsy.network

import com.example.tweetsy.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetsyApi {

    @GET("v3/b/65a679a21f5677401f1e8461?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<TweetListItem>>

    @GET("v3/b/65a679a21f5677401f1e8461?meta=false")
    @Headers("X-JSON-Path:tweets..category") // Using Headers annotation because here we're passing a static header value.
    suspend fun getCategories(): Response<List<String>>

}