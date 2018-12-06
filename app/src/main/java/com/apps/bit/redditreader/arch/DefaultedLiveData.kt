package uk.co.dictate.dit3app.android.architecture

import androidx.lifecycle.MutableLiveData

open class DefaultedLiveData<T : Any>(defaultValue: T) : MutableLiveData<T>() {

    init {
        value = defaultValue
    }

    override fun getValue() = super.getValue()!!
}