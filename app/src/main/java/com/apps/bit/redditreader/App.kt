package com.apps.bit.redditreader

import android.app.Application
import com.apps.bit.redditreader.repository.Repository
import com.apps.bit.redditreader.repository.database.ObjectBoxDB
import com.apps.bit.redditreader.repository.rest.RetrofitBuilder
import com.apps.bit.redditreader.repository.sharedPreferences.AndroidPreferences
import com.apps.bit.redditreader.workers.FeedRefreshWorker

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Repository.init(
                ObjectBoxDB(cacheDir),
                RetrofitBuilder.build(BASE_URL),
                AndroidPreferences(this)
        )

        FeedRefreshWorker.schedule(Repository.getUpdateInterval())
    }
}