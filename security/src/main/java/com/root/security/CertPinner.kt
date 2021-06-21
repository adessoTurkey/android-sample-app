package com.root.security

import okhttp3.OkHttpClient
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * val builder = OkHttpClient.Builder()
 * trustStore.trust(
 * "alias",
 * CertificateUtility.fromFile(
 * filesDir.path,
 * "app_certificate",
 * CertificateUtility.Extension.DER)
 * )
 * AdessoSecurityProvider
 * .getOkHttpCertPinner()
 * .pin(builder, socketProvider.getFactory(), trustStore.getTrustManagers()[0])
 *
 * created on 20.06.2021
 */
interface CertPinner {

    /**
     * Trusts given certificate with http client
     */
    fun pin(
        okHttpBuilder: OkHttpClient.Builder,
        socketFactory: SSLSocketFactory,
        trustManager: X509TrustManager
    )
}