package com.root.security.crypto.asymmetric

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec
import java.security.spec.KeySpec

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 19.12.2021
 */
class AsymmetricKeyGenerator(
    private val keyAlgorithm: KeyAlgorithm
) {

    companion object {
        private const val KEY_LENGTH = 2048
        private const val STD_NAME = "secp256k1"
    }

    val keyPair: KeyPair? = null
        get() = field ?: generateKeyPair()

    fun generatePublicKey(keySpec: KeySpec): PublicKey {
        return KeyFactory.getInstance(keyAlgorithm.value).generatePublic(keySpec)
    }

    fun generatePrivateKey(keySpec: KeySpec): PrivateKey {
        return KeyFactory.getInstance(keyAlgorithm.value).generatePrivate(keySpec)
    }

    private fun generateKeyPair(): KeyPair {
        return if (keyAlgorithm == KeyAlgorithm.ECDSA) {
            getEcdsaKeyPair()
        } else {
            getRsaKeyPair()
        }
    }

    private fun getRsaKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator
            .getInstance(KeyAlgorithm.RSA.value)
        keyPairGenerator
            .initialize(
                KEY_LENGTH, SecureRandom()
            )
        return keyPairGenerator.genKeyPair()
    }

    private fun getEcdsaKeyPair(): KeyPair {
        val ecSpec = ECGenParameterSpec(STD_NAME)
        val keyPairGenerator = KeyPairGenerator
            .getInstance(KeyAlgorithm.ECDSA.value)
        keyPairGenerator.initialize(ecSpec, SecureRandom())
        return keyPairGenerator.genKeyPair()
    }
}
