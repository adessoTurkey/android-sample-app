package com.root.security

import android.annotation.TargetApi
import android.os.Build
import javax.net.ssl.SSLSocketFactory

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * trustStore.trust(
 * "alias", CertificateUtility.fromFile(
 * filesDir.path,
 * "test_certificate",
 * CertificateUtility.Extension.CER
 * )
 * )
 * if("android" >= 16 && android <= 19) {
 * "otherHttpClients"."sslSocketFactory" = socketProvider.getFactoryCompat()
 * }else {
 * "otherHttpClients"."sslSocketFactory" = socketProvider.getFactory()
 * }
 *
 * created on 20.06.2021
 */
interface SocketProvider {

    /**
     * Default TLS Socket factory to create safe connections
     * greater than > 19
     */
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    fun getFactory(): SSLSocketFactory

    /**
     * If your app is targeting [Build.VERSION_CODES.JELLY_BEAN]
     * use this for between [Build.VERSION_CODES.JELLY_BEAN] - [Build.VERSION_CODES.KITKAT]
     * to enable TLS aka SSL
     */
    fun getFactoryCompat(): SSLSocketFactory
}