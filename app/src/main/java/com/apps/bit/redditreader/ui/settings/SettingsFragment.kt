package com.apps.bit.redditreader.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.apps.bit.redditreader.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }
}