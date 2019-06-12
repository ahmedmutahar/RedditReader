package com.apps.bit.redditreader.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.apps.bit.redditreader.util.inflateView

abstract class BaseFragment : Fragment() {
    @get: LayoutRes
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            container?.inflateView(layoutRes)
}