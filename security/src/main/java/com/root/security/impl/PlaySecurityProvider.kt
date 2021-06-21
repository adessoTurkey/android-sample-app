package com.root.security.impl;

import android.content.Context
import android.content.Intent
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.root.security.SecurityProvider

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * Update your security provider to protect against SSL exploits
 * Provided by google play security
 * this class requires google play dependencies
 * for light weight -> 'com.google.android.gms:play-services-basement:17.6.0'
 *
 * created on 19.06.2021
 */
internal class PlaySecurityProvider private constructor(private val context: Context) :
    SecurityProvider {

    companion object {
        fun getWithContext(context: Context): SecurityProvider = PlaySecurityProvider(context)
    }

    @Throws(
        GooglePlayServicesRepairableException::class,
        GooglePlayServicesNotAvailableException::class
    )
    override fun update() {
        ProviderInstaller.installIfNeeded(context)
    }

    override fun updateAsync(callback: SecurityProvider.ProviderCallback?) {
        ProviderInstaller.installIfNeededAsync(
            context,
            object : ProviderInstaller.ProviderInstallListener {

                override fun onProviderInstalled() {
                    callback?.onSuccess()
                }

                override fun onProviderInstallFailed(errorCode: Int, recoveryInten: Intent?) {
                    callback?.onFail()
                }

            })
    }
}