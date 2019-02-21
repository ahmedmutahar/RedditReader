package com.apps.bit.redditreader.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import java.lang.reflect.ParameterizedType
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> Any.getGenericsClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.first() as Class<T>

fun ViewGroup.inflateView(@LayoutRes id: Int) = LayoutInflater
        .from(context)
        .inflate(id, this, false)

@Suppress("DEPRECATION")
fun TextView.setHtmlText(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }
}

fun trace(vararg args: Any?): Unit = args
        .joinToString()
        .let {
            Log.d("---", it)
        }

fun Any?.trace() = trace(this)

fun Context.openURL(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (couldResolve(intent)) {
        startActivity(intent)
    }
}

fun Context.couldResolve(intent: Intent) = intent.resolveActivity(packageManager) != null

operator fun SharedPreferences.get(key: String, defValue: String): String = getString(key, defValue)!!
operator fun SharedPreferences.get(key: String, defValue: Int): Int = getInt(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Boolean): Boolean = getBoolean(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Float): Float = getFloat(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Long): Long = getLong(key, defValue)
operator fun SharedPreferences.get(key: String, defValue: Set<String>): Set<String> = getStringSet(key, defValue)!!

operator fun SharedPreferences.set(key: String, value: String) = edit().putString(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Int) = edit().putInt(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Boolean) = edit().putBoolean(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Float) = edit().putFloat(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Long) = edit().putLong(key, value).apply()
operator fun SharedPreferences.set(key: String, value: Set<String>) = edit().putStringSet(key, value).apply()

fun <T> ObjectBoxLiveData<T>.single(): LiveData<T> = MediatorLiveData<T>()
        .also { mld ->
            mld.addSource(this) {
                mld.value = it.firstOrNull()
            }
        }

fun <T> Box<T>.replace(values: List<T>) = store.runInTx {
    removeAll()
    put(values)
}

val Date.asDateTimeString get() = DateFormatTransformer.getDateTimeFormat().format(this)

inline fun tryCatching(action: () -> Unit) {
    try {
        action()
    } catch (t: Throwable) {
    }
}