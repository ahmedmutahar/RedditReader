package com.apps.bit.redditreader.ui.feed

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.TransitionInflater
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.arch.ArchFragment
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.util.instantiateFragment
import com.apps.bit.redditreader.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : ArchFragment<FeedViewModel>(), SwipeRefreshLayout.OnRefreshListener {

    override val layoutRes get() = R.layout.fragment_feed

    private val postsAdapter = PostsAdapter(::onPostClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feed_swipe_refresh.setOnRefreshListener(this)
        feed_recycler.adapter = postsAdapter
    }

    override fun onViewLifecycleReady() {
        super.onViewLifecycleReady()
        viewModel.posts.observe(::onPostsUpdated)
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

    fun onPostClick(post: Entry, view: View) {
        val ctx = context ?: return
        ctx
                .instantiateFragment<PostFragment> {
                    putSerializable(Entry::class.java.canonicalName, post)
                }
                .apply {
                    sharedElementEnterTransition = TransitionInflater.from(ctx).inflateTransition(android.R.transition.move)
                }
                .let {
                    fragmentManager!!
                            .beginTransaction()
                            .replace(R.id.fragment_container, it)
                            .addSharedElement(view, view.transitionName)
                            .addToBackStack(PostFragment::class.java.name)
                            .commit()
                }
    }
}