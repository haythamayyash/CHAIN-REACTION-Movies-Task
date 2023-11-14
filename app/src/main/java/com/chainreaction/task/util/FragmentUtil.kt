package com.chainreaction.task.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String?) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}