package com.apps.bit.redditreader.repository.rest

import android.content.Context
import com.apps.bit.redditreader.BuildConfig
import com.apps.bit.redditreader.util.Converter
import com.apps.bit.redditreader.util.trace
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

object NetworkBuilder {
    private val client = OkHttpClient
        .Builder()
        .addInterceptor(createLoggingInterceptor())
        .cookieJar(createCookieJar())
        .build()

    fun buildService(url: String) = Retrofit
        .Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Converter.xmlConverter))
        .build()
        .create<RestApi>()

    fun buildPicasso(context: Context) = Picasso
        .Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()

    private fun createLoggingInterceptor() = HttpLoggingInterceptor(createLogger()).setLevel(
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }
    )

    private fun createLogger() = HttpLoggingInterceptor.Logger {
        trace(it, "HTTP")
    }

    private fun createCookieJar() = object : CookieJar {
        private val store = mutableMapOf<HttpUrl, Set<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val prev = store[url] ?: emptySet()
            store[url] = prev + cookies
        }

        override fun loadForRequest(url: HttpUrl) = (store[url] ?: emptySet()).toList()
    }
}