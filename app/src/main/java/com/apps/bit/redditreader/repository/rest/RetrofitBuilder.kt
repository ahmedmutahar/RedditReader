package com.apps.bit.redditreader.repository.rest

import com.apps.bit.redditreader.BuildConfig
import com.apps.bit.redditreader.util.Converter
import com.apps.bit.redditreader.util.trace
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

object RetrofitBuilder : HttpLoggingInterceptor.Logger {
    fun build(apiUrl: String) = Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .client(createHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Converter.xmlConverter))
            .build()
            .create<RestApi>()

    private fun createHttpClient() = OkHttpClient
            .Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()

    private fun getLoggingInterceptor() = HttpLoggingInterceptor(this).apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }
    }

    override fun log(message: String) = trace(message)
}