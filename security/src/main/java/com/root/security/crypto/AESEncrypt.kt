package com.root.security.crypto

import android.util.Base64
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
class AESEncrypt(private val input: String) {

    companion object {
        private const val CRYPTO_ALGORITHM = "AES/CBC/PKCS5Padding"
        private const val KEY_ALGORITHM = "AES"
        private const val KEY_LENGTH = 256
        private const val IV_LENGTH = 16
    }

    fun encrypt(): ByteArray =
        encrypt(input = input, key = generateKey(), iv = generateIv())

    fun encode(cipherText: ByteArray): String = Base64.encodeToString(cipherText, 0)

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
        val cipher: Cipher = Cipher.getInstance(CRYPTO_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        return cipher.doFinal(input.toByteArray())
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM)
        keyGenerator.init(KEY_LENGTH)
        return keyGenerator.generateKey()
    }

    private fun generateIv(): IvParameterSpec {
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }
}
