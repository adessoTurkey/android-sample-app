package com.root.security.dsl

import com.root.security.crypto.aes.AesConfig
import com.root.security.crypto.aes.AesDecrypt
import com.root.security.crypto.aes.AesEncrypt
import com.root.security.crypto.asymmetric.AsymmetricKeyGenerator
import com.root.security.crypto.asymmetric.KeyAlgorithm
import com.root.security.crypto.dsa.DigitalSignature
import com.root.security.crypto.hash.SecureHash
import com.root.security.encoding.EncodingType
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * val abc = aesEncrypt("haci")
 * val bytes = aesEncryptBytes("haci")
 * val asd = aesEncrypt("haci") { specs = AesAlgorithmSpecs.GcmSpecs()}
 * val asd = aesEncrypt("haci") { specs = AesAlgorithmSpecs.CbcSpecs()}
 *
 * val keyData = KeyData()
 * val asd = aesEncrypt("haci") { exportKey = keyData }
 * val asd = aesDecrypt("haci") { importKey = keyData }
 *
 * see [com.root.security.CryptoModuleTest] for more detail
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

inline fun sha256(block: () -> String): String {
    val sha = SecureHash(SecureHash.Algorithm.SHA2_256, EncodingType.BASE64)
    return sha.digest(block())
}

inline fun sha512(block: () -> String): String {
    val sha = SecureHash(SecureHash.Algorithm.SHA2_512, EncodingType.BASE64)
    return sha.digest(block())
}

inline fun sha(
    algorithm: SecureHash.Algorithm? = null,
    encodingType: EncodingType? = null,
    block: () -> String
): String {
    val sha = SecureHash(
        algorithm ?: SecureHash.Algorithm.SHA2_256,
        encodingType ?: EncodingType.BASE64
    )
    return sha.digest(block())
}

inline fun rsaSign(value: String, block: (KeyPair, String) -> Unit) {
    val keyPair = AsymmetricKeyGenerator(KeyAlgorithm.RSA).keyPair
    val signature = DigitalSignature(KeyAlgorithm.RSA, EncodingType.HEX)
    val signedValue = signature.sign(value.toByteArray(), keyPair!!.private)
    block(keyPair, signature.encode(signedValue))
}

inline fun rsaSign(value: String, privateKey: PrivateKey, block: (String) -> Unit) {
    val signature = DigitalSignature(KeyAlgorithm.RSA, EncodingType.HEX)
    val signedValue = signature.sign(value.toByteArray(), privateKey)
    block(signature.encode(signedValue))
}

fun rsaVerify(value: String, signedValue: String, publicKey: PublicKey): Boolean {
    val signature = DigitalSignature(KeyAlgorithm.RSA, EncodingType.HEX)
    return with(signature) {
        verify(value.toByteArray(), decode(signedValue), publicKey)
    }
}
