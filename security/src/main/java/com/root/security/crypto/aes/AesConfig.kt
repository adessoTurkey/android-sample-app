package com.root.security.crypto.aes

import javax.crypto.Cipher

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class AesConfig(
    var specs: AesAlgorithmSpecs = AesAlgorithmSpecs.CbcSpecs(),
    var operation: OpMode,
    var exportKey: AesKeySpecs? = null,
    var importKey: AesKeySpecs? = null
) {
    enum class OpMode(val mode: Int) {
        ENCRYPT(Cipher.ENCRYPT_MODE),
        DECRYPT(Cipher.DECRYPT_MODE)
    }
}
