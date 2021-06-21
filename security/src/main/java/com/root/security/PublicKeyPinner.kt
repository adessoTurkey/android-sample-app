package com.root.security

import java.security.cert.Certificate
import okhttp3.CertificatePinner

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * publicKeyPinner
 * .add(
 * "*.adesso.com",
 * CertificateUtility.fromAssets(this)[0],
 * PublicKeyPinner.Algorithm.SHA_1
 * )
 * .add("**.adesso.com", CertificateUtility.fromAssets(this)[0])
 * .add("www.adesso.com", CertificateUtility.fromAssets(this)[0])
 * .pin()
 *
 * created on 20.06.2021
 */
interface PublicKeyPinner {

    fun add(
        pattern: String,
        certificate: Certificate,
        algorithm: Algorithm = Algorithm.SHA_256
    ): PublicKeyPinner

    /**
     * Finish pinning
     */
    fun pin(): CertificatePinner

    enum class Algorithm(val digestVal: String, val pinnerVal: String) {
        /**
         * Compatibility values
         */
        SHA_1("SHA-1", "sha1/"),

        /**
         * Compatibility values
         */
        SHA_256("SHA-256", "sha256/"),
    }
}
