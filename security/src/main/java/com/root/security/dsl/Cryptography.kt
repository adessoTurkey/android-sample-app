package com.root.security.dsl

import com.root.security.crypto.AESEncrypt

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * val abc = aes { "haci" }
 * val bytes = aesBytes { "haci" }
 *
 * created on 27.06.2021
 */

inline fun aes(block: () -> String) = AESEncrypt(block()).run { encode(encrypt()) }

inline fun aesBytes(block: () -> String) = AESEncrypt(block()).encrypt()
