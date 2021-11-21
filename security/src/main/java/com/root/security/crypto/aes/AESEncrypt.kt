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
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * created on 27.06.2021
 */
class AESEncrypt(private val input: String, private val config: AesConfig) {

    fun encrypt(): ByteArray =
        encrypt(input = input, key = getKey(), iv = getIv())

    fun encode(cipherText: ByteArray): String = config.specs.encodingType().encoder.encode(cipherText)

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    private fun encrypt(
        input: String,
        key: SecretKey,
        iv: IvParameterSpec
    ): ByteArray {
        val cipher: Cipher = Cipher.getInstance(config.specs.algorithmName())
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        return cipher.doFinal(input.toByteArray())
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun getKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(config.specs.keyLengthInBytes() * 8)
        val secret = keyGenerator.generateKey()
        config.exportKey?.let {
            it.secretKey = secret.encoded
        }
        return secret
    }

    private fun getIv(): IvParameterSpec {
        val iv = ByteArray(config.specs.ivLengthInBytes())
        SecureRandom().nextBytes(iv)
        config.exportKey?.let {
            it.iv = iv
        }
        return IvParameterSpec(iv)
    }
}
