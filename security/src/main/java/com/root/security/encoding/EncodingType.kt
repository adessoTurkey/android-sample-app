package com.root.security.encoding

import com.root.security.encoding.base64.Base64Decoder
import com.root.security.encoding.base64.Base64Encoder
import com.root.security.encoding.hex.HexDecoder
import com.root.security.encoding.hex.HexEncoder

enum class EncodingType(val encoder: Encoder, val decoder: Decoder) {
    HEX(HexEncoder(), HexDecoder()),
    BASE64(Base64Encoder(), Base64Decoder()),
}
