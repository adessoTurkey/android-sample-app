package com.root.security.crypto.aes

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * created on 27.06.2021
 */
class AesDecrypt(private val input: String, private val config: AesConfig) {

    companion object {
        private const val KEY_ALGORITHM = "AES"
        private const val BIT_FACTOR = 8
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun transaction(): ByteArray {
        var secret: ByteArray? = null
        var iv: ByteArray? = null
        var aad: ByteArray? = null
        if (config.importKey != null) {
            secret = config.importKey?.secretKey?.let { decode(it) }
            iv = config.importKey?.iv?.let { decode(it) }
            aad = config.importKey?.aad?.let { decode(it) }
        }
        val cipher = createCipher(secret, iv, aad)
        val decoded = decode((input))
        return cipher.doFinal(decoded)
    }

    private fun createCipher(secret: ByteArray?, iv: ByteArray?, aad: ByteArray?): Cipher {
        val cipher: Cipher = Cipher.getInstance(config.specs.algorithmName())
        if (config.specs is AesAlgorithmSpecs.GcmSpecs) {
            cipher.init(
                Cipher.DECRYPT_MODE,
                SecretKeySpec(secret, KEY_ALGORITHM),
                GCMParameterSpec(
                    BIT_FACTOR * config.specs.authenticationTagLengthInBytes(),
                    iv
                )
            )
            aad?.let { cipher.updateAAD(it) }
        } else {
            cipher.init(
                Cipher.DECRYPT_MODE,
                SecretKeySpec(secret, KEY_ALGORITHM),
                IvParameterSpec(iv)
            )
        }

        return cipher
    }

    fun encode(plainText: ByteArray): String = String(plainText, Charsets.UTF_8)

    private fun decode(cipherText: String): ByteArray =
        config.specs.encodingType().decoder.decode(cipherText)
}
