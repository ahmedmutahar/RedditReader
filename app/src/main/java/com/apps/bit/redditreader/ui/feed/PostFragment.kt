package com.apps.bit.redditreader.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.arch.BaseFragment
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.util.asDateTimeString
import com.apps.bit.redditreader.util.openURL
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.item_post.*

class PostFragment : BaseFragment() {

    override val layoutRes get() = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val post = arguments!!.getSerializable(Entry::class.java.canonicalName) as Entry

        date.text = post.updated?.asDateTimeString

        author.text = post.author?.name
        author.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))

        category.text = post.category?.term

        title.text = post.title

        post.content?.let {
            content_view.loadDataWithBaseURL(null, it, "text/html", "UTF-8", null)
        }
        post.author?.uri?.let { url ->
            author.setOnClickListener { it.context.openURL(url) }
        }
        post.link?.href?.let { url ->
            button_open_in_browser.setOnClickListener { it.context.openURL(url) }
        }

        header.transitionName = post.id.toString()
    }
}