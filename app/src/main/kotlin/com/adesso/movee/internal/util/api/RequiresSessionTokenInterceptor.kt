package com.adesso.movee.internal.util.api

import android.content.Context
import com.adesso.movee.R
import com.adesso.movee.data.local.datasource.LoginLocalDataSource
import com.adesso.movee.internal.util.Failure
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

const val HEADER_REQUIRE_SESSION_TOKEN =
    "${RequiresSessionTokenInterceptor.HEADER_KEY}: ${RequiresSessionTokenInterceptor.HEADER_VALUE}"

class RequiresSessionTokenInterceptor @Inject constructor(
    @ApplicationContext context: Context,
    private val loginLocalDataSource: LoginLocalDataSource
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header(HEADER_KEY)?.toBoolean() == true) {
            val url = request.url.newBuilder()
                .addQueryParameter(
                    QUERY_PARAMETER_SESSION,
                    loginLocalDataSource.getSessionToken()
                        ?: throw Failure.UnAuthError(
                            applicationContext.getString(R.string.common_error_unauthorized)
                        )
                )
                .build()

            request = request.newBuilder()
                .url(url)
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val QUERY_PARAMETER_SESSION = "session_id"
        const val HEADER_KEY = "RequireSessionToken"
        const val HEADER_VALUE = "true"
    }
}
