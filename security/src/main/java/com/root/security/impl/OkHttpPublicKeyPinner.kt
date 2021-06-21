package com.root.security.impl

import android.util.Base64
import com.root.security.PublicKeyPinner
import java.security.MessageDigest
import java.security.cert.Certificate
import okhttp3.CertificatePinner

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * Public Key Pinning
 *
 * FULL_DOMAIN
 * valid -> www.blablabla.com
 * invalid -> us-west.www.blabla.com
 *
 * ANY_SUB_DOMAINS
 * valid -> **.blabla.com, www.blabla.com, us-west.www.blabla.com, blabla.com
 *
 * valid -> *.blabla.com, api.blabla.com
 * EXACTLY_ONE
 *
 * created on 20.06.2021
 */
internal class OkHttpPublicKeyPinner : PublicKeyPinner {

    companion object {
        fun getDefault(): PublicKeyPinner = OkHttpPublicKeyPinner()
    }

    private val pinnerBuilder = CertificatePinner.Builder()

    /**
     * add certificate public keys
     */
    override fun add(
        pattern: String,
        certificate: Certificate,
        algorithm: PublicKeyPinner.Algorithm
    ): PublicKeyPinner {
        pinnerBuilder.apply {
            add(pattern, algorithm.pinnerVal + toBase64(certificate.publicKey.encoded, algorithm))
        }
        return this
    }

    /**
     * Finish pinning
     */
    override fun pin(): CertificatePinner = pinnerBuilder.build()

    private fun toBase64(byteArray: ByteArray, algorithm: PublicKeyPinner.Algorithm): String {
        val digester = MessageDigest.getInstance(algorithm.digestVal)
        digester.update(byteArray)
        val decoded = digester.digest()
        return String(Base64.encode(decoded, 0))
    }
}
