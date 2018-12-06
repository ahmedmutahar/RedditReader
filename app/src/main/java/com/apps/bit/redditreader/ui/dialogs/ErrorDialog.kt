package com.apps.bit.redditreader.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class ErrorDialog(context: Context) : DialogInterface.OnClickListener {

    private val alertDialog = AlertDialog
            .Builder(context)
            .setPositiveButton(android.R.string.ok, this)
            .create()

    override fun onClick(dialog: DialogInterface?, which: Int) = Unit

    fun show(t: Throwable) = show(t.localizedMessage ?: "", t::class.java.simpleName)

    fun show(message: String, title: String? = null) = with(alertDialog) {
        setMessage(message)
        setTitle(title)
        show()
    }

    fun dismiss() = alertDialog.dismiss()
}