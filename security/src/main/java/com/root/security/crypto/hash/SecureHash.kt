package com.root.security.crypto.hash

import com.root.security.encoding.EncodingType
import java.security.MessageDigest

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 18.12.2021
 */
class SecureHash(algorithm: Algorithm, private val encoders: EncodingType) {

    enum class Algorithm(val value: String) {
        SHA2_256("SHA-256"),
        SHA2_512("SHA-512"),
        SHA3_256("SHA3-256"),
        SHA3_512("SHA3-512"),
    }

    private val digest = MessageDigest.getInstance(algorithm.value)

    fun digest(data: String): String {
        val hash = digestBytes(data)
        return encoders.encoder.encode(hash)
    }

    private fun digestBytes(data: String): ByteArray {
        return digest.digest(data.toByteArray())
    }
}
