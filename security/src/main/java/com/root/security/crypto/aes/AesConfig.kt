package com.root.security.crypto.aes;

/**
 * @author kanal
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
class AesConfig(
    var specs: AesAlgorithmSpecs = AesAlgorithmSpecs.CbcSpecs(),
    var exportKey: ExportKey? = null,
    var importKey: ImportKey? = null
)
