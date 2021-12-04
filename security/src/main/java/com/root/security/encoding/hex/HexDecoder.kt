package com.root.security.encoding.hex

import com.root.security.encoding.Decoder

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class HexDecoder : Decoder {

    override fun decode(encoded: String): ByteArray =
        encoded.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}
