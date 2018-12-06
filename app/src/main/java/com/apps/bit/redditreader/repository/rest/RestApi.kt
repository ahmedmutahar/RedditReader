package com.apps.bit.redditreader.repository.rest

import com.apps.bit.redditreader.model.Feed
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {
    companion object {
        private const val RSS_SUFFIX = ".rss"
    }

    @GET(RSS_SUFFIX)
    fun getPostsFromMain(): Deferred<Feed>

    @GET("r/{subreddit}/$RSS_SUFFIX")
    fun getPostsForSubreddit(
            @Path("subreddit") subreddit: String
    ): Deferred<Feed>
}