package com.root.security.encoding

interface Decoder {
    fun decode(encoded: String): ByteArray
}
