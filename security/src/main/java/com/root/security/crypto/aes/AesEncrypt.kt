package com.root.security.crypto.aes

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
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
class AesEncrypt(private val input: String, private val config: AesConfig) {

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
        val secret: ByteArray?
        val iv: ByteArray?
        var aad: ByteArray? = null
        if (config.importKey != null) {
            secret = config.importKey?.secretKey?.let { decode(it) }
            iv = config.importKey?.iv?.let { decode(it) }
            aad = config.importKey?.aad?.let { decode(it) }
        } else {
            secret = generateKey()
            iv = generateIv()
            config.exportKey?.let {
                it.secretKey = encode(secret)
                it.iv = encode(iv)
                it.aad = aad?.let { encode(aad) }
            }
        }
        val cipher = createCipher(secret, iv, aad)
        return cipher.doFinal(input.toByteArray())
    }

    private fun createCipher(secret: ByteArray?, iv: ByteArray?, aad: ByteArray?): Cipher {
        val cipher: Cipher = Cipher.getInstance(config.specs.algorithmName())
        if (config.specs is AesAlgorithmSpecs.GcmSpecs) {
            aad?.let { cipher.updateAAD(it) }
            cipher.init(
                Cipher.ENCRYPT_MODE,
                SecretKeySpec(secret, KEY_ALGORITHM),
                GCMParameterSpec(
                    BIT_FACTOR * config.specs.authenticationTagLengthInBytes(),
                    iv
                )
            )
        } else {
            cipher.init(
                Cipher.ENCRYPT_MODE,
                SecretKeySpec(secret, KEY_ALGORITHM),
                IvParameterSpec(iv)
            )
        }

        return cipher
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun generateKey(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
        keyGenerator.init(BIT_FACTOR * config.specs.keyLengthInBytes())
        val secret = keyGenerator.generateKey()
        return secret.encoded
    }

    private fun generateIv(): ByteArray {
        val iv = ByteArray(config.specs.ivLengthInBytes())
        SecureRandom().nextBytes(iv)
        return iv
    }

    fun encode(cipherText: ByteArray): String =
        config.specs.encodingType().encoder.encode(cipherText)

    private fun decode(cipherText: String): ByteArray =
        config.specs.encodingType().decoder.decode(cipherText)
}
