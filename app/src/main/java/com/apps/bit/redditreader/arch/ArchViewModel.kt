package com.apps.bit.redditreader.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.co.dictate.dit3app.android.architecture.DefaultedLiveData

abstract class ArchViewModel : ViewModel() {
    val isLoading = DefaultedLiveData(false)
    val errorAction = ActionLiveData<Throwable>()

    val defaultCoroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        errorAction.observeForever { it?.printStackTrace() }
    }

    inline fun withLoadingAndError(crossinline action: suspend () -> Unit) = defaultCoroutineScope.launch {
        try {
            isLoading.value = true
            action()
        } catch (t: Throwable) {
            errorAction.setValue(t)
        } finally {
            isLoading.value = false
        }
    }
}