package com.apps.bit.redditreader.util

import org.simpleframework.xml.transform.Transform
import java.text.SimpleDateFormat
import java.util.*

object DateFormatTransformer : Transform<Date> {

    private val patterns = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss"
    )

    override fun write(value: Date): String {
        return getDateFormat(patterns.first()).format(value)
    }

    override fun read(value: String): Date? {
        patterns.forEach {
            tryCatching {
                return getDateFormat(it).parse(value)
            }
        }
        return null
    }

    fun getDateTimeFormat() = SimpleDateFormat.getDateTimeInstance()

    private fun getDateFormat(pattern: String) = SimpleDateFormat(pattern, Locale.ENGLISH)
}