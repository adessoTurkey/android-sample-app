package com.root.security.impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.root.security.SocketProvider
import com.root.security.impl.socket.TLSSocketFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 * created on 20.06.2021
 */
internal class SocketFactoryProvider : SocketProvider {

    companion object {
        fun getDefault(): SocketProvider = SocketFactoryProvider()
    }

    private val protocol = "TLS"
    private val sslContext = SSLContext.getInstance(protocol)

    /**
     * Always use recommended(default) implementations
     * if you do not have better approach
     */
    init {
        sslContext.init(null, null, null)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    override fun getFactory(): SSLSocketFactory = sslContext.socketFactory

    override fun getFactoryCompat(): SSLSocketFactory = TLSSocketFactory.withSSlContext(sslContext)
}
