package com.apps.bit.redditreader.repository.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.apps.bit.redditreader.R
import com.apps.bit.redditreader.util.get

class AndroidPreferences(context: Context) : PreferencesApi {
    private val settingsPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val subredditKey = context.resources.getString(R.string.settings_subreddit_key)
    private val refreshIntervalKey = context.resources.getString(R.string.settings_refresh_key)

    private val subredditDefault = context.resources.getString(R.string.settings_subreddit_default)
    private val refreshIntervalDefault = context.resources.getString(R.string.settings_refresh_default)

    override val subreddit get() = settingsPreferences[subredditKey, subredditDefault]
    override val updateInterval get() = settingsPreferences[refreshIntervalKey, refreshIntervalDefault].toLong()

    override fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
            settingsPreferences.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
            settingsPreferences.unregisterOnSharedPreferenceChangeListener(listener)
}