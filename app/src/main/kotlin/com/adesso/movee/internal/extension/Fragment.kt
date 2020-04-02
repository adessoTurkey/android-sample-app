package com.adesso.movee.internal.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(msg, duration)
}
