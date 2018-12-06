package com.apps.bit.redditreader.viewmodel

import com.apps.bit.redditreader.arch.ArchViewModel
import com.apps.bit.redditreader.repository.Repository

class PostViewModel : ArchViewModel() {

    fun getPostObservable(postId: String) = Repository.getPostObservable(postId)
}