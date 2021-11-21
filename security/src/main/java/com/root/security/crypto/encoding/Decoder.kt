package com.root.security.crypto.encoding

interface Decoder {
    fun decode(encoded: String): ByteArray
}
