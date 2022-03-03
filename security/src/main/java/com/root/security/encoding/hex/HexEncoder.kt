package com.root.security.encoding.hex

import com.root.security.encoding.Encoder

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class HexEncoder : Encoder {

    override fun encode(plainText: ByteArray): String = plainText.joinToString("") {
        java.lang.String.format("%02x", it)
    }
}
