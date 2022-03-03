package com.root.security.encoding

interface Encoder {
    fun encode(plainText: ByteArray): String
}
