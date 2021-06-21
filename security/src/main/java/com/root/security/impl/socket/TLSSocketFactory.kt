package com.root.security.impl.socket;

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 20.06.2021
 */
internal class TLSSocketFactory private constructor(sslContext: SSLContext) : SSLSocketFactory() {

    companion object {
        /**
         * SSL context must be initialized before using
         */
        fun withSSlContext(sslContext: SSLContext) = TLSSocketFactory(sslContext)
    }

    private val socketFactory = sslContext.socketFactory

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket =
        enableTLS(socketFactory.createSocket(s, host, port, autoClose))

    override fun createSocket(host: String?, port: Int): Socket =
        enableTLS(socketFactory.createSocket(host, port))

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket = enableTLS(socketFactory.createSocket(host, port, localHost, localPort))

    override fun createSocket(host: InetAddress?, port: Int): Socket =
        enableTLS(socketFactory.createSocket(host, port))

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket = enableTLS(socketFactory.createSocket(address, port, localAddress, localPort))

    override fun getDefaultCipherSuites(): Array<String> = socketFactory.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = socketFactory.supportedCipherSuites

    /**
     * Manually enable TLS implementation
     */
    private fun enableTLS(socket: Socket): Socket {
        if (socket is SSLSocket) {
            socket.enabledProtocols = arrayOf("TLSv1.1", "TLSv1.2")
        }
        return socket
    }
}