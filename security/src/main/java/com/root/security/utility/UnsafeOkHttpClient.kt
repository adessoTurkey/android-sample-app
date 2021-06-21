package com.root.security.utility

import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * This is an implementation for testing purposes, do not use this on production
 *
 * created on 20.06.2021
 */
object UnsafeOkHttpClient {

    fun get(): OkHttpClient {
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        val tms = createTrustManager()
        sslContext.init(null, tms, null)
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, tms[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        return builder.build()
    }

    /**
     * Trusts everyone
     */
    private fun createTrustManager() = arrayOf(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })
}
