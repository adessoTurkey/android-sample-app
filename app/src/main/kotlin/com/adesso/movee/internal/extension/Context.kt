package com.adesso.movee.internal.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.adesso.movee.internal.popup.Popup
import com.adesso.movee.internal.popup.PopupCallback
import com.adesso.movee.internal.popup.PopupUiModel

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context?.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.showPopup(uiModel: PopupUiModel, callback: PopupCallback? = null) {
    Popup(this, uiModel, callback).show()
}