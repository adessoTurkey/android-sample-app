package com.root.security.crypto.aes

import android.os.Build
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
class AESEncrypt(private val input: String, private val config: AesConfig) {

    companion object {
        private const val KEY_ALGORITHM = "AES"
        private const val DATA_MULTIPLIER = 8
    }

    fun encode(cipherText: ByteArray): String =
        config.specs.encodingType().encoder.encode(cipherText)

    private fun decode(cipherText: String): ByteArray =
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
        var secret: ByteArray? = config.importKey?.secretKey?.let { decode(it) }
        var iv: ByteArray? = config.importKey?.iv?.let { decode(it) }
        var aad: ByteArray? = config.importKey?.aad?.let { decode(it) }
        if (config.importKey == null) {
            secret = getKey()
            iv = getIv()
        }
        createExportKey(secret, iv, aad)
        val cipher: Cipher = createCipher(secret, iv, aad)
        return runCipher(cipher)
    }

    private fun runCipher(cipher: Cipher): ByteArray {
        val result = if (config.operation == AesConfig.OpMode.DECRYPT) {
            val decoded = decode((input))
            cipher.doFinal(
                decoded
            )
        } else {
            cipher.doFinal(input.toByteArray())
        }
        return result
    }

    private fun createExportKey(
        secret: ByteArray?,
        iv: ByteArray?,
        aad: ByteArray?
    ) {
        config.exportKey?.let { specs ->
            specs.secretKey = secret?.let { encode(it) }
            specs.iv = iv?.let { encode(it) }
            specs.aad = aad?.let { encode(it) }
        }
    }

    private fun createCipher(secret: ByteArray?, iv: ByteArray?, aad: ByteArray?): Cipher {
        val cipher: Cipher = Cipher.getInstance(config.specs.algorithmName())
        if (config.specs is AesAlgorithmSpecs.GcmSpecs) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                throw RuntimeException("AES GCM supports api 19 or above")
            }
            cipher.init(
                config.operation.mode,
                SecretKeySpec(secret, KEY_ALGORITHM),
                GCMParameterSpec(
                    config.specs.authenticationTagLengthInBytes() * DATA_MULTIPLIER,
                    iv
                )
            )
            aad?.let { cipher.updateAAD(it) }
        } else {
            cipher.init(
                config.operation.mode,
                SecretKeySpec(secret, KEY_ALGORITHM),
                IvParameterSpec(iv)
            )
        }

        return cipher
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun getKey(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
        keyGenerator.init(config.specs.keyLengthInBytes() * DATA_MULTIPLIER)
        val secret = keyGenerator.generateKey()
        return secret.encoded
    }

    private fun getIv(): ByteArray {
        val iv = ByteArray(config.specs.ivLengthInBytes())
        SecureRandom().nextBytes(iv)
        return iv
    }
}
