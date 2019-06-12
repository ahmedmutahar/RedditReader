package com.apps.bit.redditreader.viewmodel

import com.apps.bit.redditreader.arch.ArchViewModel
import com.apps.bit.redditreader.repository.Repository

class FeedViewModel : ArchViewModel() {

    val posts = Repository.getPostsObservable()

    init {
        getPosts()
    }

    fun getPosts() = withLoadingAndError {
        Repository.updatePosts()
    }
}