package com.root.security

import com.root.security.crypto.aes.AesKeySpecs
import com.root.security.dsl.aesDecrypt
import com.root.security.dsl.aesEncrypt
import org.junit.Assert
import org.junit.Test

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class CryptoModuleTest {

    @Test
    fun test_Aes_256_Cbc_encryption() {
        // Given
        val plainText = "haci"
        val keyData = AesKeySpecs()
        // When
        val cipherText = aesEncrypt(plainText) {
            this.exportKey = keyData
        }

        val decrypted = aesDecrypt(cipherText) {
            this.importKey = keyData
        }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }
}
