@file:Suppress("DEPRECATION")

package com.apps.bit.redditreader.repository.rest

import com.apps.bit.redditreader.BuildConfig
import com.apps.bit.redditreader.util.Converter
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

object RetrofitBuilder {
    fun build(apiUrl: String) = Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .client(createHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Converter.xmlConverter))
            .build()
            .create<RestApi>()

    private fun createHttpClient() = OkHttpClient
            .Builder()
            .cookieJar(getCookieJar())
            .addInterceptor(getLoggingInterceptor())
            .build()

    private fun getLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
    )

    private fun getCookieJar() = object : CookieJar {
        private val store = mutableMapOf<HttpUrl, Set<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val prev = store[url] ?: emptySet()
            store[url] = prev + cookies
        }

        override fun loadForRequest(url: HttpUrl) = (store[url] ?: emptySet()).toList()
    }
}