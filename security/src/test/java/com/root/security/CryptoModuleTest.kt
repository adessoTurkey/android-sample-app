package com.root.security

import android.os.Build.VERSION
import com.root.security.crypto.aes.AesAlgorithmSpecs
import com.root.security.crypto.aes.keyspecs.AesKeySpecs
import com.root.security.dsl.aesDecrypt
import com.root.security.dsl.aesEncrypt
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import org.junit.Assert
import org.junit.Test

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class CryptoModuleTest {

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }

    @Test
    fun test_Aes_256_Cbc_encryption() {
        // Given
        val plainText = "haci"
        val keyData = AesKeySpecs()
        // When
        val cipherText = aesEncrypt(plainText) { exportKey = keyData }

        val decrypted = aesDecrypt(cipherText) { importKey = keyData }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }

    @Test
    fun test_Aes_256_Cbc_with_custom_key_encryption() {
        // Given
        val plainText = "haci"
        val keyData = AesKeySpecs()
        keyData.secretKey = "6D5A7134743777217A25432A462D4A614E645267556B58703272357538782F41"
        keyData.iv = "6D5A71346D5A71346D5A71346D5A7134"
        keyData.aad = "6D5A71346D5A71346D5A7134"
        // When
        val cipherText = aesEncrypt(plainText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        val decrypted = aesDecrypt(cipherText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }

    @Test
    fun test_Aes_256_Gcm_encryption() {
        // Given
        val plainText = "haciİŞ^^!%"
        val keyData = AesKeySpecs()
        setFinalStatic(VERSION::class.java.getField("SDK_INT"), 19)

        // When
        val cipherText = aesEncrypt(plainText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            exportKey = keyData
        }

        val decrypted = aesDecrypt(cipherText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }
}
