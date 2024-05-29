package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import android.util.Log
import com.google.gson.GsonBuilder
import com.vasscompany.vassuniversitybaseproject.BuildConfig
import com.vasscompany.vassuniversitybaseproject.data.constants.GeneralConstants.Companion.RETROFIT_TIMEOUT_IN_SECOND
import com.vasscompany.vassuniversitybaseproject.data.session.DataUserSession
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

@Singleton
class RetrofitClientBaseProject @Inject constructor(
    val dataUserSession: DataUserSession
) {
    companion object {
        const val HEADER_KEY_TOKEN = "Authorization"
        const val HEADER_VALUE_TOKEN_START = "Bearer "

        private const val SHA256_POKEMON = "sha256/0+wMWHAE8yi0RhlZJ9xvVvMuvMOJu+xma8+uesoEvoY="
    }

    val retrofit: Retrofit

    init {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder()
            .add("pokeapi.co", SHA256_POKEMON)
            .build()
        httpClient.certificatePinner(certificatePinner)

        val hostnamesAllow = listOf(
            "pokeapi.co",
        )
        val hostnameVerifier = HostnameVerifier { hostname, session ->
            hostname in hostnamesAllow
        }
        httpClient.hostnameVerifier(hostnameVerifier)

        httpClient
            .connectTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .writeTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

        httpClient.interceptors().clear()
        httpClient.interceptors().add(Interceptor { chain ->
            val original = chain.request()

            val request = when {
                needAddBearer(chain.request()) -> {
                    original.newBuilder()
                        .header(HEADER_KEY_TOKEN, HEADER_VALUE_TOKEN_START + dataUserSession.tokenIb)
                        .method(original.method, original.body)
                        .build()
                }

                else -> {
                    original.newBuilder()
                        .method(original.method, original.body)
                        .build()
                }
            }

            chain.proceed(request)
        })

        if (BuildConfig.DEBUG) {
            // Creamos un interceptor y le indicamos el log level a usar
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }

        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_VASS_UNIVERSITY_BASE_PROJECT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    private fun needAddBearer(request: Request): Boolean {
        val buffer = okio.Buffer()
        request.body?.writeTo(buffer)
        val requestUrl = request.url.toString()

        return when {
            requestUrl.endsWith("oauth/v2/token", true) -> {
                Log.d(TAG, "l> No needAddBearer endsWith(oauth/v2/token")
                false
            }

            requestUrl.endsWith("oauth/v2/token/revoke", true) -> {
                Log.d(TAG, "l> No needAddBearer endsWith(oauth/v2/token/revoke")
                false
            }

            requestUrl.contains("R4PDFGenerator/Transferencia.do", true) -> {
                Log.d(TAG, "l> No needAddBearer contains(R4PDFGenerator/Transferencia.do)")
                false
            }

            dataUserSession.tokenIb.isNotBlank() -> {
                Log.d(TAG, "l> NeedAddBearer")
                true
            }

            else -> {
                Log.d(TAG, "l> No needAddBearer contemplated")
                false
            }
        }
    }
}