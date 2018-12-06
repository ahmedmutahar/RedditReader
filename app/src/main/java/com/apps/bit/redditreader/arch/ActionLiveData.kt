package com.apps.bit.redditreader.arch

import androidx.lifecycle.LiveData

class ActionLiveData<T> : LiveData<T>() {

    public override fun setValue(value: T) {
        super.setValue(value)
        super.setValue(null)
    }
}