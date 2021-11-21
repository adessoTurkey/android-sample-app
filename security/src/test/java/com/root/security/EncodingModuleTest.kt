package com.root.security;

import com.root.security.crypto.encoding.hex.HexDecoder
import com.root.security.crypto.encoding.hex.HexEncoder
import org.junit.Assert
import org.junit.Test

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class EncodingModuleTest {

    private val hexValue = "6c6f72656dc59e7073756d646f6c6f722073c4b074616d6574"
    private val plainText = "loremŞpsumdolor sİtamet"

    @Test
    fun test_hex_encoder() {
        // given
        val encoder = HexEncoder()

        // when
        val encoded = encoder.encode(plainText.toByteArray())

        // then
        Assert.assertEquals(hexValue, encoded)
    }

    @Test
    fun test_hex_decoder() {
        // given
        val decoder = HexDecoder()

        // when
        val decoded = decoder.decode(hexValue)

        // then
        Assert.assertEquals(plainText, String(decoded, Charsets.UTF_8))
    }
}
