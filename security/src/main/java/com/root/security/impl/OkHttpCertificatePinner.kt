package com.root.security.impl

import com.root.security.CertPinner
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 20.06.2021
 */
internal class OkHttpCertificatePinner private constructor() : CertPinner {

    companion object {
        fun getDefault(): CertPinner = OkHttpCertificatePinner()
    }

    override fun pin(
        okHttpBuilder: OkHttpClient.Builder,
        socketFactory: SSLSocketFactory,
        trustManager: X509TrustManager
    ) {
        okHttpBuilder.sslSocketFactory(socketFactory, trustManager)
    }
}
