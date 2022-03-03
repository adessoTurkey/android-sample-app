package com.root.security.ssl

import android.content.Context
import com.root.security.ssl.impl.OkHttpCertificatePinner
import com.root.security.ssl.impl.OkHttpPublicKeyPinner
import com.root.security.ssl.impl.PlaySecurityProvider
import com.root.security.ssl.impl.SocketFactoryProvider
import com.root.security.ssl.impl.TrustStoreProvider

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * Sample usage
 *
 * val playSecurity = AdessoSecurityProvider.getDefaultSecurityProvider(this)
 * val trustStore = AdessoSecurityProvider.getTrustStore()
 * val socketProvider = AdessoSecurityProvider.getSocketProvider()
 * val publicKeyPinner = AdessoSecurityProvider.getOkHttpPublicKeyPinner()
 * val certPinner = AdessoSecurityProvider.getOkHttpCertPinner()
 *
 *
 *
 * created on 19.06.2021
 */
object AdessoSecurityProvider {

    /**
     * Accept certificates
     */
    fun getTrustStore() = TrustStoreProvider.getDefault()

    /**
     * Generate public key pinner
     */
    fun getOkHttpPublicKeyPinner() = OkHttpPublicKeyPinner.getDefault()

    /**
     * Generate certificate pinner
     */
    fun getOkHttpCertPinner() = OkHttpCertificatePinner.getDefault()

    /**
     * Generate safe or unsafe sockets
     */
    fun getSocketProvider() = SocketFactoryProvider.getDefault()

    /**
     * Check device security provider
     */
    fun getDefaultSecurityProvider(context: Context) =
        PlaySecurityProvider.getWithContext(context)
}
