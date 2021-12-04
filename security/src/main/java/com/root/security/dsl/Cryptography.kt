package com.root.security.dsl

import com.root.security.crypto.aes.AesConfig
import com.root.security.crypto.aes.AesDecrypt
import com.root.security.crypto.aes.AesEncrypt

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * val abc = aesEncrypt("haci")
 * val bytes = aesEncrypt("haci")
 * val asd = aesEncrypt("haci") { specs = AesAlgorithmSpecs, secretKey = bytes, iv = bytes }
 * val asd = aesEncrypt("haci") { specs = AesAlgorithmSpecs, secretKey = bytes, iv = bytes }
 *
 * created on 27.06.2021
 */
inline fun aesEncrypt(plainText: String, block: AesConfig.() -> Unit = {}): String {
    val config = AesConfig()
    block(config)
    return AesEncrypt(plainText, config).run { encode(transaction()) }
}

inline fun aesEncryptBytes(plainText: String, block: AesConfig.() -> Unit = {}): ByteArray {
    val config = AesConfig()
    block(config)
    return AesEncrypt(plainText, config).transaction()
}

inline fun aesDecrypt(cipherText: String, block: AesConfig.() -> Unit = {}): String {
    val config = AesConfig()
    block(config)
    return AesDecrypt(cipherText, config).run { encode(transaction()) }
}

inline fun aesDecryptBytes(cipherText: String, block: AesConfig.() -> Unit = {}): ByteArray {
    val config = AesConfig()
    block(config)
    return AesDecrypt(cipherText, config).run { transaction() }
}
