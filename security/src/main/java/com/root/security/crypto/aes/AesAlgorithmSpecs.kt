package com.root.security.crypto.aes

import com.root.security.crypto.encoding.EncodingType

interface AesAlgorithmSpecs {

    fun algorithmName(): String
    fun keyLengthInBytes(): Int
    fun ivLengthInBytes(): Int
    fun authTagLengthInBytes(): Int
    fun encodingType(): EncodingType

    class CbcSpecs : AesAlgorithmSpecs {

        override fun algorithmName(): String = "AES/CBC/PKCS5Padding"

        override fun keyLengthInBytes(): Int = 32

        override fun ivLengthInBytes(): Int = 16

        override fun authTagLengthInBytes(): Int = 0

        override fun encodingType(): EncodingType = EncodingType.HEX
    }

    class GcmSpecs : AesAlgorithmSpecs {

        override fun algorithmName(): String = "AES/GCM/NoPadding"

        override fun keyLengthInBytes(): Int = 32

        override fun ivLengthInBytes(): Int = 12

        override fun authTagLengthInBytes(): Int = 16

        override fun encodingType(): EncodingType = EncodingType.BASE64
    }
}
