package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.BuildConfig
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseProjectApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class RetrofitInterceptor @Inject constructor(private val baseProjectApplication: BaseProjectApplication) : Interceptor {

    companion object {
        const val HEADER_KEY_TOKEN = "Authorization"
        const val HEADER_VALUE_TOKEN_START = "Bearer "
        const val HEADER_AUTH = "X-Auth-Token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = chain.request().url
        val serviceName = url.pathSegments.lastOrNull { it.isNotEmpty() }?.split(".")?.firstOrNull() ?: ""
        val response: Response?

        val request = original.newBuilder()
            .method(original.method, original.body)

        addHeadersBasedOnRequestAnnotations(original, request)

        response = chain.proceed(request.build())

        val jsonResponse: String
        if (response.isSuccessful) {
            jsonResponse = if (serviceName.lowercase() == "login") {
                val authResp = response.headers[HEADER_AUTH]
                if (authResp != null) {
                    baseProjectApplication.saveAuthToken(authResp)
                }
                if (response.body?.string()!!.isEmpty()) {
                    "{}"
                } else {
                    response.body?.string().toString()
                }
            } else {
                response.body?.string().toString()
            }
        } else {
            jsonResponse = response.body?.string().toString()
        }
        return response.newBuilder().body(jsonResponse.toResponseBody(response.body?.contentType())).build()
    }

    private fun addHeadersBasedOnRequestAnnotations(original: Request, request: Request.Builder) {
        if (!original.url.toString().contains("login")) {
            addAuthorizationHeader(request)
        }
    }

    private fun addAuthorizationHeader(request: Request.Builder) {
        request.addHeader(HEADER_KEY_TOKEN, HEADER_VALUE_TOKEN_START + getBearerToken())
    }

    private fun addXAuthTokenIfNotEmpty(request: Request.Builder) {
        val auth = baseProjectApplication.getAuthToken()
        if (auth.isNotEmpty()) {
            request.header(HEADER_AUTH, auth)
        }
    }

    private fun getBearerToken(): String {
        return when (BuildConfig.FLAVOR) {
            "des" -> {
                baseProjectApplication.getBearerTokenDes()
            }

            "pro" -> {
                baseProjectApplication.getBearerTokenPro()
            }

            else -> ""
        }
    }
}