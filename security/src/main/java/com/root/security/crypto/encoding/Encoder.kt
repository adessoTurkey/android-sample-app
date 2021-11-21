package com.root.security.crypto.encoding

interface Encoder {
    fun encode(plainText: ByteArray): String
}
