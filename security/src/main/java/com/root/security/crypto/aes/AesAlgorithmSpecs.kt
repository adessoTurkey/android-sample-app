package com.root.security.crypto.aes

import android.os.Build
import androidx.annotation.RequiresApi
import com.root.security.crypto.encoding.EncodingType

interface AesAlgorithmSpecs {

    fun algorithmName(): String
    fun keyLengthInBytes(): Int
    fun ivLengthInBytes(): Int
    fun authenticationTagLengthInBytes(): Int
    fun encodingType(): EncodingType

    class CbcSpecs : AesAlgorithmSpecs {

        override fun algorithmName(): String = "AES/CBC/PKCS5Padding"

        override fun keyLengthInBytes(): Int = 32

        override fun ivLengthInBytes(): Int = 16

        override fun authenticationTagLengthInBytes(): Int = 0

        override fun encodingType(): EncodingType = EncodingType.HEX
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    class GcmSpecs : AesAlgorithmSpecs {

        override fun algorithmName(): String = "AES/GCM/NoPadding"

        override fun keyLengthInBytes(): Int = 32

        override fun ivLengthInBytes(): Int = 16

        override fun authenticationTagLengthInBytes(): Int = 12

        override fun encodingType(): EncodingType = EncodingType.HEX
    }
}
