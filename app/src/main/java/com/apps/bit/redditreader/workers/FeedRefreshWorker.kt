package com.apps.bit.redditreader.workers

import android.content.Context
import androidx.work.*
import com.apps.bit.redditreader.model.Minute
import com.apps.bit.redditreader.repository.Repository
import com.apps.bit.redditreader.util.trace

class FeedRefreshWorker(
        context: Context,
        params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {

        private const val REFRESH_WORK_NAME = "REFRESH_WORK_NAME"
        private const val REFRESH_DELAY_WORK_NAME = "REFRESH_DELAY_WORK_NAME"

        fun schedule(initialDelay: Minute) = OneTimeWorkRequestBuilder<Delay>()
                .setInitialDelay(initialDelay.value, initialDelay.timeUnit)
                .build()
                .let {
                    WorkManager
                            .getInstance()
                            .enqueueUniqueWork(
                                    REFRESH_DELAY_WORK_NAME,
                                    ExistingWorkPolicy.REPLACE,
                                    it
                            )
                }
                .also {
                    trace("scheduled refresh job, with initial delay: $initialDelay")
                }
    }

    override suspend fun doWork() = try {
        Repository.updatePosts()
        Result.success()
    } catch (t: Throwable) {
        Result.retry()
    }

    class Delay(
            context: Context,
            params: WorkerParameters
    ) : Worker(context, params) {

        override fun doWork() = Repository
                .getUpdateInterval()
                .let { interval ->
                    PeriodicWorkRequestBuilder<FeedRefreshWorker>(
                            interval.value,
                            interval.timeUnit
                    )
                }
                .setConstraints(
                        Constraints
                                .Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                )
                .build()
                .let {
                    WorkManager
                            .getInstance()
                            .enqueueUniquePeriodicWork(
                                    REFRESH_WORK_NAME,
                                    ExistingPeriodicWorkPolicy.REPLACE,
                                    it
                            )
                    Result.success()
                }
    }
}