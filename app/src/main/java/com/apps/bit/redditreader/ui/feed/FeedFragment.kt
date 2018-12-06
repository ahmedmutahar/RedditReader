package com.apps.bit.redditreader.ui.feed

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.arch.ArchFragment
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.ui.FragmentController
import com.apps.bit.redditreader.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : ArchFragment<FeedViewModel>(), SwipeRefreshLayout.OnRefreshListener {

    override val layoutRes get() = R.layout.fragment_feed

    private val postsAdapter = PostsAdapter(::onPostClick)

    private lateinit var fragmentController: FragmentController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentController = context as FragmentController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feed_swipe_refresh.setOnRefreshListener(this)
        feed_recycler.adapter = postsAdapter
    }

    override fun onViewLifecycleReady() {
        super.onViewLifecycleReady()
        viewModel.posts.observe(::onPostsUpdated)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPosts()
    }

    override fun onLoadingStatusUpdate(isLoading: Boolean) {
        feed_swipe_refresh.isRefreshing = isLoading
    }

    override fun onRefresh() {
        viewModel.getPosts()
    }

    fun onPostsUpdated(entries: List<Entry>) {
        postsAdapter.submitList(entries)
    }

    fun onPostClick(post: Entry) {
        fragmentController.changeFragment(PostFragment.create(post.postId!!))
    }
}