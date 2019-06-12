@file:Suppress("DEPRECATION")

package com.apps.bit.redditreader.repository.rest

import com.apps.bit.redditreader.BuildConfig
import com.apps.bit.redditreader.util.Converter
import com.apps.bit.redditreader.util.trace
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

object RetrofitBuilder {

    @JvmStatic
    fun build(apiUrl: String) = Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .client(OkHttpClient
                    .Builder()
                    .addInterceptor(createLoggingInterceptor())
                    .cookieJar(createCookieJar())
                    .build())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Converter.xmlConverter))
            .build()
            .create<RestApi>()

    @JvmStatic
    private fun createLoggingInterceptor() = HttpLoggingInterceptor(createLogger()).setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
    )

    @JvmStatic
    private fun createLogger() = HttpLoggingInterceptor.Logger {
        trace(it, "HTTP")
    }

    @JvmStatic
    private fun createCookieJar() = object : CookieJar {
        private val store = mutableMapOf<HttpUrl, Set<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val prev = store[url] ?: emptySet()
            store[url] = prev + cookies
        }

        override fun loadForRequest(url: HttpUrl) = (store[url] ?: emptySet()).toList()
    }
}