package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import android.util.Log
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RetrofitTimeLogCallsInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startTime = System.nanoTime()
        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        val duration = (endTime - startTime) / 1_000_000.0 // Duración en milisegundos

        Log.d(TAG, "l> --> TimingInterceptor - La llamada a ${request.url} tomó ${duration}ms")

        return response
    }

}