package com.root.security

import java.security.cert.Certificate
import javax.net.ssl.X509TrustManager

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * created on 19.06.2021
 */
interface TrustStore {

    /**
     * adds trustworthy certificate to trust store
     */
    fun trust(aliasName: String, certificate: Certificate?)

    /**
     * Trust managers
     */
    fun getTrustManagers(): List<X509TrustManager>
}
