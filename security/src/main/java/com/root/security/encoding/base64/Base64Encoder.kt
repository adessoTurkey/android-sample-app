package com.root.security.encoding.base64

import android.util.Base64
import com.root.security.encoding.Encoder

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class Base64Encoder : Encoder {
    override fun encode(plainText: ByteArray): String =
        Base64.encodeToString(plainText, Base64.DEFAULT)
}
