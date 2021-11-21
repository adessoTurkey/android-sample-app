package com.root.security.crypto.encoding

import com.root.security.crypto.encoding.base64.Base64Decoder
import com.root.security.crypto.encoding.base64.Base64Encoder
import com.root.security.crypto.encoding.hex.HexDecoder
import com.root.security.crypto.encoding.hex.HexEncoder

enum class EncodingType(val encoder: Encoder, val decoder: Decoder) {
    HEX(HexEncoder(), HexDecoder()),
    Base64(Base64Encoder(), Base64Decoder()),
}
