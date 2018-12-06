package com.apps.bit.redditreader.util

import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

object Converter {
    val xmlConverter = RegistryMatcher()
            .apply {
                bind(Date::class.java, DateFormatTransformer)
            }
            .let(::Persister)

    fun serialize(_object: Any) = ByteArrayOutputStream()
            .also {
                xmlConverter.write(_object, it)
            }
            .toByteArray()

    inline fun <reified T> deserialize(data: ByteArray) =
            xmlConverter.read(T::class.java, ByteArrayInputStream(data))
}