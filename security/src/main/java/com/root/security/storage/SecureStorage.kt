package com.root.security.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 4.12.2021
 */
class SecureStorage(private val fileName: String, private val appContext: Context) {

    /**
     * Apply or commit should be calledd
     */
    fun putString(key: String, value: String): SharedPreferences.Editor {
        val sharedPreferences = EncryptedSharedPreferences.create(
            appContext,
            fileName,
            getOrCreateMasterKey(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        with(sharedPreferences.edit()) {
            putString(key, value)
            return this
        }
    }

    private fun getOrCreateMasterKey(): MasterKey = MasterKey.Builder(appContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .setRequestStrongBoxBacked(true)
        .build()
}
