package com.apps.bit.redditreader.repository.database

import androidx.lifecycle.LiveData
import com.apps.bit.redditreader.model.Entry
import kotlinx.coroutines.Job

interface DatabaseApi {

    fun setPosts(posts: List<Entry>): Job

    fun getPostsObservable(): LiveData<List<Entry>>

    fun getPostObservable(postId: String): LiveData<Entry>
}