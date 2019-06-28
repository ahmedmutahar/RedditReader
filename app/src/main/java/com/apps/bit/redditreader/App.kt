package com.apps.bit.redditreader

import android.annotation.SuppressLint
import android.app.Application
import com.apps.bit.redditreader.repository.Repository
import com.apps.bit.redditreader.repository.database.ObjectBoxDB
import com.apps.bit.redditreader.repository.rest.NetworkBuilder
import com.apps.bit.redditreader.repository.sharedPreferences.AndroidPreferences
import com.apps.bit.redditreader.workers.FeedRefreshWorker
import com.squareup.picasso.Picasso

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var picassoInstance: Picasso
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Repository.init(
                ObjectBoxDB(cacheDir),
            NetworkBuilder.buildService(BASE_URL),
                AndroidPreferences(this)
        )

        picassoInstance = NetworkBuilder.buildPicasso(this)

        FeedRefreshWorker.schedule(Repository.getUpdateInterval())
    }
}