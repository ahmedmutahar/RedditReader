package com.apps.bit.redditreader.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*


abstract class ArchViewModel : ViewModel(), CoroutineScope {
    val isLoading = DefaultedLiveData(false)
    val errorAction = ActionLiveData<Throwable>().apply {
        observeForever {
            it?.printStackTrace()
        }
    }

    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    protected inline fun withLoadingAndError(crossinline action: suspend () -> Unit) = launch {
        try {
            isLoading.value = true
            action()
        } catch (t: Throwable) {
            errorAction.setValue(t)
        } finally {
            isLoading.value = false
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
    }
}