package com.apps.bit.redditreader.model

import java.util.concurrent.TimeUnit

inline class Minute(
        val value: Long
) {
    val timeUnit get() = TimeUnit.MINUTES
}