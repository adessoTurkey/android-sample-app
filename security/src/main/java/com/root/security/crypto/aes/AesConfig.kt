package com.root.security.crypto.aes

import com.root.security.crypto.aes.keyspecs.AesKeySpecs

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class AesConfig(
    var specs: AesAlgorithmSpecs = AesAlgorithmSpecs.CbcSpecs(),
    var importKey: AesKeySpecs? = null,
    var exportKey: AesKeySpecs? = null
)
