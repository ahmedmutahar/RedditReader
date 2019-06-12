package com.apps.bit.redditreader.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.arch.BaseFragment
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.util.asDateTimeString
import com.apps.bit.redditreader.util.fromHtml
import com.apps.bit.redditreader.util.into
import com.apps.bit.redditreader.util.openURL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : BaseFragment() {

    override val layoutRes get() = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val post = arguments!!.getSerializable(Entry::class.java.canonicalName) as Entry

        post.author?.let {
            author.text = it.name
            author.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            it.uri?.let { uri ->
                author.setOnClickListener { v -> v.context.openURL(uri) }
            }
        }
        post.category?.let {
            category.text = it.term
        }
        post.title?.let {
            title.text = it
        }
        post.updated?.let {
            date.text = it.asDateTimeString
        }
        post.content?.let {
            content_view.text = it.fromHtml()
        }
        post.imgUri?.let {
            progress.visibility = View.VISIBLE
            Picasso.get().load(it).into(image) {
                progress.visibility = View.GONE
            }
        }
    }
}