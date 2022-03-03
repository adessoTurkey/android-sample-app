package com.root.security.utility

import android.content.Context
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * Utility for handling certificates
 *
 * <p>In the case of a certificate factory for X.509 certificates, the
 * certificate provided in {@code inStream} must be DER-encoded and
 * may be supplied in binary or printable (Base64) encoding. If the
 * certificate is provided in Base64 encoding, it must be bounded at
 * the beginning by -----BEGIN CERTIFICATE-----, and must be bounded at
 * the end by -----END CERTIFICATE-----.
 *
 * created on 19.06.2021
 */
object CertificateUtility {

    private const val certificateStandard = "X.509"
    private val certificateFactory = CertificateFactory.getInstance(certificateStandard)

    /**
     * Generate certificate from file
     * path context.filesDir
     * name host
     * extension .cer
     */
    @Throws(FileNotFoundException::class, CertificateException::class)
    fun fromFile(path: String, name: String, ext: Extension): Certificate? {
        val file = if (path.endsWith("/")) {
            File("$path$name${ext.extension}")
        } else {
            File("$path/$name${ext.extension}")
        }
        if (file.exists()) {
            val stream: InputStream = FileInputStream(file)
            return certificateFactory.generateCertificate(stream)
        }
        return null
    }

    /**
     * Returns empty if no certificate file found
     * or
     * all certificates found in assets
     */
    @Throws(CertificateException::class)
    fun fromAssets(context: Context): List<Certificate> {
        val assets = context.assets
        val fileNames = assets.list("")
            ?.filter {
                it.contains(Extension.CER.extension) ||
                    it.contains(Extension.DER.extension) ||
                    it.contains(Extension.CRT.extension) ||
                    it.contains(Extension.PEM.extension)
            }
        val certificates = mutableListOf<Certificate>()
        fileNames?.forEach {
            val stream: InputStream = BufferedInputStream(assets.open(it))
            certificates.add(certificateFactory.generateCertificate(stream))
        }
        return certificates
    }

    /**
     * Extension is just file format, not encoding.
     */
    enum class Extension(val extension: String) {
        CER(".cer"),
        DER(".der"),
        CRT(".crt"),
        PEM(".pem")
    }
}
