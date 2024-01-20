package com.example.tweetsy.repository

import com.example.tweetsy.models.TweetListItem
import com.example.tweetsy.network.TweetsyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetsRepository @Inject constructor(private val tweetsyApi: TweetsyApi) {
    // Since the TweetsyApi is already provided from hilt module, we'll inject it over here like this.

    private val _categories = MutableStateFlow<List<String>>(emptyList()) // not exposing mutable object.
    // as we want to restrict right to modify category data only to this repository.
    val categories: StateFlow<List<String>> // exposing non mutable.
        get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>>
        get() = _tweets

    suspend fun getCategories() {
        val response = tweetsyApi.getCategories()
        if (response.isSuccessful && response.body() != null) {
            _categories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String) {
        val response = tweetsyApi.getTweets(category)
        if (response.isSuccessful && response.body() != null) {
            _tweets.emit(response.body()!!)
        }
    }

}