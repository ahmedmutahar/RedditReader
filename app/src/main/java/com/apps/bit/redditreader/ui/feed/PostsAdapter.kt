package com.apps.bit.redditreader.ui.feed

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.model.Entry
import com.apps.bit.redditreader.util.asDateTimeString
import com.apps.bit.redditreader.util.inflateView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(
        private val onClickListener: (Entry, View) -> Unit
) : ListAdapter<Entry, PostsAdapter.PostViewHolder>(PostsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PostViewHolder(parent.inflateView(R.layout.item_post))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
            holder.bind(getItem(position))

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val date = v.date
        private val author = v.author
        private val category = v.category
        private val title = v.title
        private val image = v.image

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) = onClickListener(getItem(adapterPosition), itemView)

        fun bind(post: Entry) {
            date.text = post.updated?.asDateTimeString
            author.text = post.author?.name
            category.text = post.category?.term
            title.text = post.title
            Picasso.get().load(post.imgUri).fit().centerCrop().placeholder(R.drawable.ic_image_black).into(image)

            itemView.transitionName = post.id.toString()
        }
    }

    private class PostsDiffUtil : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry) = oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry) = oldItem == newItem
    }
}