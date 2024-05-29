package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import android.util.Log
import com.google.gson.Gson
import com.vasscompany.vassuniversitybaseproject.BuildConfig
import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.error.ErrorResponse
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import retrofit2.Response

abstract class BaseService {
    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): BaseResponse<T> {
        val response: Response<T>
        try {
            response = call.invoke()

            return if (!response.isSuccessful) {
                val errorResponse = mapErrorResponse(response)
                Log.e(TAG, "l> errorResponse: ${errorResponse.message}")
                BaseResponse.Error(errorResponse)
            } else {
                response.body()?.let { body ->
                    BaseResponse.Success(body)
                } ?: BaseResponse.Error(mapErrorResponse(response))
            }
        } catch (throwable: Throwable) {
            Log.e(TAG, "l> throwable: ${throwable.message}")
            throwable.printStackTrace()
            return BaseResponse.Error(mapErrorResponse(throwable))
        }
    }

    private fun <T> mapErrorResponse(response: Response<T>): ErrorModel {
        val errorBody = response.errorBody()?.string()
        val errorData = try {
            val parsedData = Gson().fromJson(errorBody, ErrorResponse::class.java)
            if (response.code() == 401) {
                parsedData.errorCode = 401.toString()
            }
            parsedData
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "l> exception: ${exception.message}")
            exception.printStackTrace()
            null
        }

        return ErrorModel(errorData?.error ?: "", errorData?.errorCode ?: "0", errorData?.message ?: "")
    }

    private fun mapErrorResponse(throwable: Throwable): ErrorModel {
        return if (BuildConfig.DEBUG) {
            ErrorModel("UNKNOW", "UNKNOW", throwable.message ?: "UNKNOW")
        } else {
            ErrorModel(
                "Lo sentimos, estamos presentando problemas de conexión.", "0", "Vuelve a intentarlo más tarde."
            )
        }
    }
}