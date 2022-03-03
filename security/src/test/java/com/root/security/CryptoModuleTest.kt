package com.root.security

import android.os.Build.VERSION
import com.root.security.crypto.aes.AesAlgorithmSpecs
import com.root.security.crypto.aes.keyspecs.AesKeySpecs
import com.root.security.crypto.asymmetric.AsymmetricKeyGenerator
import com.root.security.crypto.asymmetric.KeyAlgorithm
import com.root.security.crypto.hash.SecureHash
import com.root.security.dsl.aesDecrypt
import com.root.security.dsl.aesEncrypt
import com.root.security.dsl.rsaSign
import com.root.security.dsl.rsaVerify
import com.root.security.dsl.sha
import com.root.security.dsl.sha256
import com.root.security.dsl.sha512
import com.root.security.encoding.EncodingType
import com.root.security.encoding.hex.HexDecoder
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.11.2021
 */
@RunWith(RobolectricTestRunner::class)
class CryptoModuleTest {

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }

    @Test
    fun test_Aes_256_Cbc_encryption() {
        // Given
        val plainText = "haci"
        val keyData = AesKeySpecs()
        // When
        val cipherText = aesEncrypt(plainText) { exportKey = keyData }

        val decrypted = aesDecrypt(cipherText) { importKey = keyData }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }

    @Test
    fun test_Aes_256_Gcm_with_custom_key_encryption() {
        // Given
        val plainText = "haci"
        val keyData = AesKeySpecs().apply {
            secretKey = "6D5A7134743777217A25432A462D4A614E645267556B58703272357538782F41"
            iv = "6D5A71346D5A71346D5A71346D5A7134"
            aad = "6D5A71346D5A71346D5A7134"
        }
        // When
        val cipherText = aesEncrypt(plainText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        val decrypted = aesDecrypt(cipherText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }

    @Test
    fun test_Aes_256_Gcm_encryption() {
        // Given
        val plainText = "haciİŞ^^!%"
        val keyData = AesKeySpecs()
        setFinalStatic(VERSION::class.java.getField("SDK_INT"), 19)

        // When
        val cipherText = aesEncrypt(plainText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            exportKey = keyData
        }

        val decrypted = aesDecrypt(cipherText) {
            specs = AesAlgorithmSpecs.GcmSpecs()
            importKey = keyData
        }

        // Then
        Assert.assertEquals(plainText, decrypted)
    }

    @Test
    fun test_Sha_One_Way_Hash() {
        // Given
        val text = "GimmeSomeSecureHash"
        // When
        val result1 = sha256 { text }
        val result2 = sha512 { text }
        val result3 = sha { text }
        val result4 = sha(SecureHash.Algorithm.SHA2_512) { text }
        val result5 = sha(SecureHash.Algorithm.SHA3_256) { text }
        val result6 = sha(SecureHash.Algorithm.SHA3_512, EncodingType.HEX) { text }

        // Then
        Assert.assertEquals(result1, result3)
        Assert.assertEquals(result2, result4)
        Assert.assertNotNull(result5)
        Assert.assertNotNull(result6)
    }

    @Test
    fun test_Rsa_Dsa() {
        // Given
        val text = "SignThisText"
        var keyPair: KeyPair? = null
        var signedText: String? = null

        // When
        rsaSign(text) { pair, signValue ->
            keyPair = pair
            signedText = signValue
        }

        // Then
        rsaVerify(text, signedText!!, keyPair!!.public)
    }

    @Test
    fun test_Rsa_Dsa_From_External_Key() {
        // Given
        val privateKey = "308204bf020100300d06092a864886f70d0101010500048204a9308204a" +
            "50201000282010100ad5d" +
            "256d123baa3dd7cb6b6d6b8ecab440c687ede359e9babc1bcec52f44d19c4239a51e8a3f6" +
            "e920e73b6c6238ee8caa98" +
            "3c43e0da14fd8eab8762e6f30169a98e6e3581b17641a754f7e5dbac0dfb881f1bf39a1" +
            "badc3355406440609f8d" +
            "ba6bf4950e8fcbe6615f80e760c6bed11ef189ac7c47fe40d7e482efb62ba7fbd70a437" +
            "a9908c1fc98c5a95f4cb97f7" +
            "2e11c167c5b4f5b02f7cc3ba09e5c0b836dd3a238bfdd82939f3e8a0f097962df400da" +
            "93adc2432bf1ce3a7ee255ee" +
            "16f6d00b1f023bcbbf9c78e4946173a5dc925a773caa4f1ffe3c775d3157200fb3be9686" +
            "fb563ed0b3fa71f12b0247a8e" +
            "e335485b859cd5ccbbf46cb59f3fc4e5e201020301000102820101008c550c08a06bbfc0" +
            "d3bf8c34448e8fb31d8417600f" +
            "5b2701cb674f16face604676ad26e5f3cea8f87dce59334671562b46d0acb7f65c8fbf" +
            "ed306829ea4328e8f7c05e63617" +
            "aabc367167419f7244d74936b56f995db26883f08229ea21845a793dfa610e5818df96" +
            "04f7dfc2ea523ae42a2c906c958" +
            "1cb125fe7f801d129b9dff1318208761de4d1b372f6d2bacd1d0491227b640c37985214" +
            "4c65e75c0dbb81181d1642efe" +
            "97282b0f0afbce73e91ab47ff2d2375a86fc77b2985c582c83c35e2048fd5a6ceb52e0d9" +
            "cdee06913c691de4ecec46022dd5" +
            "320ef237f283f41f445fdfd17bd406a066766a9e7a9ec20581ab438126b114126a1e3d47" +
            "6f46f20902818100db204796ae8d" +
            "c64aa55d4632ccf4159f1d6347ea036c3f9738611846bbce113ac22104832546be1938d25" +
            "c0d064bf6c8e6826f2ba373b9ea7" +
            "f05f803dc0065c00ae362ab3b6db6f46afbc55b51cf19a51cf5a969a28c03a75bb8e0c5a23" +
            "a3e8e359b6c67dd474dda4e144e919" +
            "cabd971577de3e1ebcbca545c3377d5ec816c5302818100ca897a46a822d4eb4ca9a9fe7d6" +
            "18eb4a0e334dd811f44e4f84b7e29f49" +
            "d5f64f32bda7fe8f319f287d20e7c59d7d6d1e7dfe90c3f3fc1c99d96df31e7fb4c991e21ac" +
            "73fb4203c89b867d67b7cd7dc6ed1e6c" +
            "817d6b6215325114e0b21f3dad13da50f334136baa24cd278949656510adfd058d1e2aeecf3" +
            "d2e55e7bbda0ddb02818100918dfe9fb3" +
            "f14eef54ab01af4aff33c18ed6dcf53032de86c7978f75a4d8bc0f28d69fd5b1942d4039c35" +
            "a0bf8cbcaf126552c1d73a4b2b17690f" +
            "36f34db3abb774015d1a2573db95bbb441c65da48b1a9ebf10dcf406c48e61f8787203cca42d2" +
            "8f1c525de5386cb382bfb1efbd23622" +
            "b78df3fb9ce93e6b79c24950055f42b0281802103d5fdcc8c98fe9e298dff60fad523569292dc" +
            "849ecf412fc446959a3d48fe90b1165" +
            "601681753b496262c590cd904b0728c6fa5a3c3f8148b3268d532dea29b09cc2dbfc85c0b8866" +
            "9cec9a4691f92a4fea4a1562bac34fc76" +
            "dcacede4ed476c700a914551218d314ea1bf3ede65298ba04fca2ed46ae5b7fc11b1c41083f028" +
            "18100baae3b0fa002e524542afb6ea18" +
            "c3e447e137a34fbf1fe71f50ae00788d9bc2459feffc08be575e489612739378382e8d5f80c2b7" +
            "0a6c144b0ebafc9ca988d77846b647ef6b" +
            "90ed3d017af2c2f5a42e3a3ba103cff26989f67789cd20f324681c02608a9c2fd2676be58fbc468" +
            "5ba4c725410db0fd2cc8fe742288650a9d8336"
        val publicKey = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100" +
            "ad5d256d123baa3dd7cb6b6d6b8ecab440c" +
            "687ede359e9babc1bcec52f44d19c4239a51e8a3f6e920e73b6c6238ee8caa983c43e0da14fd8" +
            "eab8762e6f30169a98e6e3581b17641a754f7e" +
            "5dbac0dfb881f1bf39a1badc3355406440609f8dba6bf4950e8fcbe6615f80e760c6bed11ef18" +
            "9ac7c47fe40d7e482efb62ba7fbd70a437a9" +
            "908c1fc98c5a95f4cb97f72e11c167c5b4f5b02f7cc3ba09e5c0b836dd3a238bfdd82939f3e8a" +
            "0f097962df400da93adc2432bf1ce3a7ee25" +
            "5ee16f6d00b1f023bcbbf9c78e4946173a5dc925a773caa4f1ffe3c775d3157200fb3be9686fb" +
            "563ed0b3fa71f12b0247a8ee335485b859cd" +
            "5ccbbf46cb59f3fc4e5e2010203010001"
        val text = "SignThisText"
        var signedText: String? = null
        val keyGen = AsymmetricKeyGenerator(KeyAlgorithm.RSA)
        val keyPrivate = keyGen.generatePrivateKey(
            PKCS8EncodedKeySpec(HexDecoder().decode(privateKey))
        )
        val keyPublic = keyGen.generatePublicKey(
            X509EncodedKeySpec(HexDecoder().decode(publicKey))
        )

        // When
        rsaSign(text, keyPrivate) {
            signedText = it
        }

        // Then
        rsaVerify(text, signedText!!, keyPublic)
    }
}
