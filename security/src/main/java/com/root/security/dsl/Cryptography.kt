package com.root.security.dsl

import com.root.security.crypto.aes.AESEncrypt
import com.root.security.crypto.aes.AesConfig

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * val abc = aes("haci")
 * val bytes = aesBytes("haci")
 * val asd = aes("haci") { specs = AesAlgorithmSpecs, secretKey = bytes, iv = bytes }
 * val asd = aes("haci") { specs = AesAlgorithmSpecs, secretKey = bytes, iv = bytes }
 *
 * created on 27.06.2021
 */

inline fun aes(input: String, block: AesConfig.() -> Unit = {}): String {
    val config = AesConfig()
    block(config)
    return AESEncrypt(input, config).run { encode(encrypt()) }
}

inline fun aesBytes(input: String, block: AesConfig.() -> Unit = {}): ByteArray {
    val config = AesConfig()
    block(config)
    return AESEncrypt(input, config).encrypt()
}
