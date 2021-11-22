package com.root.security.crypto.aes

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class ExportKey(
    var secretKey: ByteArray? = null,
    var iv: ByteArray? = null,
    var authTag: ByteArray? = null
)