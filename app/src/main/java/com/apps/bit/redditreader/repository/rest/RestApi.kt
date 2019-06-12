package com.apps.bit.redditreader.repository.rest

import com.apps.bit.redditreader.model.Feed
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {
    companion object {
        private const val RSS_SUFFIX = ".rss"
    }

    @GET(RSS_SUFFIX)
    suspend fun getPostsFromMainAsync(): Feed

    @GET("r/{subreddit}/$RSS_SUFFIX")
    suspend fun getPostsForSubredditAsync(
            @Path("subreddit") subreddit: String
    ): Feed
}