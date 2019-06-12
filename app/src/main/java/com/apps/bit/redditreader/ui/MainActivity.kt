package com.apps.bit.redditreader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apps.bit.redditreader.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = PagerAdapter(supportFragmentManager, resources)
        tabs.setupWithViewPager(view_pager)
    }
}
