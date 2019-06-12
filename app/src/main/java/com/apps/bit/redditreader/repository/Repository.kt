package com.apps.bit.redditreader.repository

import android.content.SharedPreferences
import com.apps.bit.redditreader.model.Minute
import com.apps.bit.redditreader.repository.database.DatabaseApi
import com.apps.bit.redditreader.repository.rest.RestApi
import com.apps.bit.redditreader.repository.sharedPreferences.PreferencesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Repository : SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var dbImpl: DatabaseApi
    private lateinit var restImpl: RestApi
    private lateinit var preferencesImpl: PreferencesApi

    fun init(dbImpl: DatabaseApi, restImpl: RestApi, prefImpl: PreferencesApi) {
        this.dbImpl = dbImpl
        this.restImpl = restImpl
        this.preferencesImpl = prefImpl

        this.preferencesImpl.registerListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                updatePosts()
            }
        }
    }

    suspend fun updatePosts() {
        val subreddit = preferencesImpl.subreddit

        val feed = if (subreddit.isEmpty()) {
            restImpl.getPostsFromMainAsync()
        } else {
            restImpl.getPostsForSubredditAsync(subreddit)
        }

        feed.entry?.let { dbImpl.setPosts(it).join() }
    }

    fun getPostsObservable() = dbImpl.getPostsObservable()

    fun getPostObservable(postId: String) = dbImpl.getPostObservable(postId)

    fun getUpdateInterval() = Minute(preferencesImpl.updateInterval)
}