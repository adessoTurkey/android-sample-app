package com.adesso.movee.data.local.delegate

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.adesso.movee.internal.extension.put
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPreference(
    private val preferences: SharedPreferences,
    private val preferenceName: String
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(preferenceName, null)
    }

    @WorkerThread
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.put {
            putString(preferenceName, value)
        }
    }
}
