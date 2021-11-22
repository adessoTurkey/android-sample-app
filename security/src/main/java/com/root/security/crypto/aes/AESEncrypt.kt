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
class AESEncrypt(private val input: String, private val config: AesConfig) {

    companion object {
        private const val KEY_ALGORITHM = "AES"
    }

    fun encode(cipherText: ByteArray): String =
        config.specs.encodingType().encoder.encode(cipherText)

    fun decode(cipherText: String): ByteArray =
        config.specs.encodingType().decoder.decode(cipherText)

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun transaction(): ByteArray {
        var secret: ByteArray? = config.importKey?.secretKey
        var iv: ByteArray? = config.importKey?.iv
        if (config.importKey == null) {
            secret = getKey()
            iv = getIv()
        }
        config.exportKey?.let {
            it.secretKey = secret
        }
        config.exportKey?.let {
            it.iv = iv
        }
        val cipher: Cipher = Cipher.getInstance(config.specs.algorithmName())
        cipher.init(
            config.operation.mode,
            SecretKeySpec(secret, KEY_ALGORITHM),
            IvParameterSpec(iv)
        )
        val plainBytes = if (config.operation == AesConfig.OpMode.DECRYPT) {
            decode(input)
        } else {
            input.toByteArray()
        }
        return cipher.doFinal(plainBytes)
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun getKey(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
        keyGenerator.init(config.specs.keyLengthInBytes() * 8)
        val secret = keyGenerator.generateKey()
        return secret.encoded
    }

    private fun getIv(): ByteArray {
        val iv = ByteArray(config.specs.ivLengthInBytes())
        SecureRandom().nextBytes(iv)
        return iv
    }
}
