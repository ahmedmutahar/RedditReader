package com.apps.bit.redditreader.repository.sharedPreferences

import android.content.SharedPreferences

interface PreferencesApi {
    val subreddit: String
    val updateInterval: Long

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}