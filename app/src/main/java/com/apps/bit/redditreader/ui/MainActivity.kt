package com.apps.bit.redditreader.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.ui.feed.FeedFragment
import com.apps.bit.redditreader.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.isEmpty()) {
            changeFragment(FeedFragment(), false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.settings -> {
            changeFragment(SettingsFragment(), true)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fr: Fragment, addToBackStack: Boolean) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fr, fr.javaClass.name)
                .run {
                    if (addToBackStack) {
                        addToBackStack(fr.javaClass.name)
                    } else {
                        this
                    }
                }
                .commit()
    }
}
