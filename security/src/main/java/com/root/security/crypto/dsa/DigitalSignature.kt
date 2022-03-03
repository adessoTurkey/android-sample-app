package com.root.security.crypto.dsa

import com.root.security.crypto.asymmetric.KeyAlgorithm
import com.root.security.encoding.EncodingType
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 19.12.2021
 */
class DigitalSignature(
    private val algorithm: KeyAlgorithm,
    private val encodingType: EncodingType
) {

    companion object {
        private const val SIGNING_ALGORITHM_RSA = "SHA256withRSA"
        private const val SIGNING_ALGORITHM_ECDSDA = "SHA256withECDSA"
    }

    private fun getSignature(): Signature {
        return if (algorithm == KeyAlgorithm.ECDSA) {
            Signature.getInstance(
                SIGNING_ALGORITHM_ECDSDA
            )
        } else {
            Signature.getInstance(
                SIGNING_ALGORITHM_RSA
            )
        }
    }

    fun sign(data: ByteArray, privateKey: PrivateKey): ByteArray {
        val signature = getSignature()
        signature.initSign(privateKey)
        signature.update(data)
        return signature.sign()
    }

    fun verify(data: ByteArray, signatureData: ByteArray, publicKey: PublicKey): Boolean {
        val signature = getSignature()
        signature.initVerify(publicKey)
        signature.update(data)
        return signature
            .verify(signatureData)
    }

    fun encode(signedBytes: ByteArray): String =
        encodingType.encoder.encode(signedBytes)

    fun decode(signedBytes: String): ByteArray =
        encodingType.decoder.decode(signedBytes)
}
