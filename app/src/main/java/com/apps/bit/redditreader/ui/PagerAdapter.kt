package com.apps.bit.redditreader.ui

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.ui.feed.FeedFragment
import com.apps.bit.redditreader.ui.settings.SettingsFragment

class PagerAdapter(fm: FragmentManager, res: Resources) : FragmentPagerAdapter(fm) {

    private val fragments = mutableListOf<Pair<String, () -> Fragment>>(
            res.getString(R.string.feed) to ::FeedFragment,
            res.getString(R.string.settings) to ::SettingsFragment
    )

    override fun getPageTitle(position: Int) = fragments[position].first

    override fun getItem(position: Int) = fragments[position].second()

    override fun getCount() = fragments.size
}