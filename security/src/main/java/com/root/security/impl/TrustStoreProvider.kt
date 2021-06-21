package com.root.security.impl;

import com.root.security.TrustStore
import java.security.KeyStore
import java.security.cert.Certificate
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * This is a class that allows trust store to trust given certificate entries.
 *
 * "aka" Certificate Pinning
 *
 * created on 19.06.2021
 */
internal class TrustStoreProvider private constructor() : TrustStore {

    companion object {
        fun getDefault(): TrustStore = TrustStoreProvider()
    }

    private val keyManagerAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
    private val keyStoreType = TrustManagerFactory.getDefaultAlgorithm()
    private val trustManager = TrustManagerFactory.getInstance(keyManagerAlgorithm)
    private val keyStore = KeyStore.getInstance(keyStoreType)

    init {
        keyStore.load(null, null)
        trustManager.init(keyStore)
    }

    override fun trust(aliasName: String, certificate: Certificate?) {
        keyStore.setCertificateEntry(aliasName, certificate)
    }

    override fun getTrustManagers(): List<X509TrustManager> {
        val managers = mutableListOf<X509TrustManager>()
        trustManager.trustManagers?.forEach {
            if (it is X509TrustManager) {
                managers.add(it)
            }
        }
        return managers
    }

}
