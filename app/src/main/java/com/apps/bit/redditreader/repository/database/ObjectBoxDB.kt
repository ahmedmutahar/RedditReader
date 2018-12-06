package com.apps.bit.redditreader.repository.database

import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.model.Entry_
import com.apps.bit.redditreader.model.MyObjectBox
import com.apps.bit.redditreader.util.replace
import com.apps.bit.redditreader.util.single
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ObjectBoxDB(baseDir: File) : DatabaseApi, CoroutineScope {

    override val coroutineContext = Dispatchers.IO

    private val cacheStore = MyObjectBox
            .builder()
            .baseDirectory(baseDir)
            .name("db_cache")
            .build()

    private val postsBox = cacheStore.boxFor(Entry::class.java)

    override fun setPosts(posts: List<Entry>) = launch {
        postsBox.replace(posts)
    }

    override fun getPostsObservable() = postsBox
            .query()
            .order(
                    Entry_.updated,
                    QueryBuilder.DESCENDING)
            .build()
            .let(::ObjectBoxLiveData)

    override fun getPostObservable(postId: String) = postsBox
            .query()
            .equal(
                    Entry_.postId,
                    postId)
            .build()
            .let(::ObjectBoxLiveData)
            .single()
}