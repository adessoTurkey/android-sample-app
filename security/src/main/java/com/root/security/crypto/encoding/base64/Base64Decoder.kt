package com.root.security.crypto.encoding.base64;

import android.util.Base64
import com.root.security.crypto.encoding.Decoder

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class Base64Decoder : Decoder {
    override fun decode(encoded: String): ByteArray = Base64.decode(encoded, 0)
}
