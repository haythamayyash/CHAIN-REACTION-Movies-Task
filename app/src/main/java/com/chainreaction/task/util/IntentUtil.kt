package com.chainreaction.task.util

import android.content.Context
import android.content.Intent

fun Context?.shareContent(textToShare: String?) {
    if (textToShare == null) {
        return
    }

    this?.startActivity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textToShare)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                type = "text/plain"
            },
            null
        )
    )
}