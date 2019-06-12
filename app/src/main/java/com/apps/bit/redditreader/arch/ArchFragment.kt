package com.apps.bit.redditreader.arch

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apps.bit.redditreader.ui.dialogs.ErrorDialog
import com.apps.bit.redditreader.util.getGenericsClass

abstract class ArchFragment<T : ArchViewModel> : BaseFragment() {
    protected val errorDialog by lazy { ErrorDialog(context!!) }
    protected val viewModel by lazy { ViewModelProviders.of(this).get(getGenericsClass<T>()) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onViewLifecycleReady()
    }

    @CallSuper
    protected open fun onViewLifecycleReady() {
        viewModel.errorAction.observe(::onError)
        viewModel.isLoading.observe(::onLoadingStatusUpdate)
    }

    protected open fun onLoadingStatusUpdate(isLoading: Boolean) = Unit

    protected open fun onError(t: Throwable) = errorDialog.show(t)

    protected inline fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) =
            observe(viewLifecycleOwner, Observer { it?.let(action) })
}