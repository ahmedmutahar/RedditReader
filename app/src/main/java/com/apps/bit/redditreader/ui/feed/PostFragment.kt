package com.apps.bit.redditreader.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.arch.ArchFragment
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.util.asDateTimeString
import com.apps.bit.redditreader.util.openURL
import com.apps.bit.redditreader.util.setHtmlText
import com.apps.bit.redditreader.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.item_post.*

class PostFragment : ArchFragment<PostViewModel>() {

    companion object {
        private const val POST_ID = "POST_ID"

        fun create(postId: String) = PostFragment().apply {
            val b = Bundle()
            b.putString(POST_ID, postId)
            arguments = b
        }
    }

    override val layoutRes get() = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        author.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }

    override fun onViewLifecycleReady() {
        super.onViewLifecycleReady()
        viewModel.getPostObservable(arguments!!.getString(POST_ID)!!).observe(::onPostLoaded)
    }

    fun onPostLoaded(post: Entry) {
        date.text = post.updated?.asDateTimeString
        author.text = post.author?.name
        category.text = post.category?.term
        title.text = post.title
        post.content?.let(content::setHtmlText)

        author.setOnClickListener {
            post.author?.uri?.let(it.context::openURL)
        }
        button_open_in_browser.setOnClickListener {
            post.link?.href?.let(it.context::openURL)
        }
    }
}
