package com.root.security.sample

import android.app.Activity
import android.os.Bundle
import com.root.security.ssl.AdessoSecurityProvider
import com.root.security.ssl.PublicKeyPinner
import com.root.security.ssl.SocketProvider
import com.root.security.ssl.TrustStore
import com.root.security.storage.SecureStorage
import com.root.security.utility.CertificateUtility
import okhttp3.OkHttpClient

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 21.06.2021
 */
class SecurityTestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playSecurity = AdessoSecurityProvider.getDefaultSecurityProvider(this)
        val trustStore = AdessoSecurityProvider.getTrustStore()
        val socketProvider = AdessoSecurityProvider.getSocketProvider()
        val publicKeyPinner = AdessoSecurityProvider.getOkHttpPublicKeyPinner()

        playSecurity.update() // This requires google play service dependency
        okHttpCertificatePinning(
            OkHttpClient.Builder(),
            trustStore, socketProvider
        ) // your app's okhttp client builder
        okHttpPublicKeyPinning(publicKeyPinner)

        SecureStorage("haci", this).putString("key", "value").apply()
    }

    private fun okHttpPublicKeyPinning(publicKeyPinner: PublicKeyPinner) {
        publicKeyPinner
            .add(
                "*.adesso.com",
                CertificateUtility.fromAssets(this)[0],
                PublicKeyPinner.Algorithm.SHA_1
            )
            .add("**.adesso.com", CertificateUtility.fromAssets(this)[0])
            .add("www.adesso.com", CertificateUtility.fromAssets(this)[0])
            .pin()
    }

    private fun okHttpCertificatePinning(
        builder: OkHttpClient.Builder,
        trustStore: TrustStore,
        socketProvider: SocketProvider
    ) {
        trustStore.trust(
            "alias",
            CertificateUtility.fromFile(
                filesDir.path,
                "app_certificate",
                CertificateUtility.Extension.DER
            )
        )
        AdessoSecurityProvider
            .getOkHttpCertPinner()
            .pin(builder, socketProvider.getFactory(), trustStore.getTrustManagers()[0])
    }
}
