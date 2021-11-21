package com.root.security;

import com.root.security.crypto.aes.ExportKey
import com.root.security.dsl.aes
import com.root.security.dsl.aesBytes
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
        val expected = "773e90ad7b3f7473fd2349747a1deb61"
        val plainText = "haci"

        // When
        val encrypted = aes(plainText)

        // Then
        Assert.assertEquals(expected, encrypted)
    }

    @Test
    fun test_Aes_256_Cbc_exportKeySpecs() {
        // Given
        val plainText = "haci"

        // When
        val exported = ExportKey()
        val decrypted = aesBytes(plainText) {
            this.exportKey = exported
        }
        // Then
//        Assert.assertEquals(expected, encrypted)
    }
}
