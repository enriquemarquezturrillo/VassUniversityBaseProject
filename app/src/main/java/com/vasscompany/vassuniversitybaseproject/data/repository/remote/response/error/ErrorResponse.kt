package com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") var error: String?,
    @SerializedName("errorCode") var errorCode: String?,
    @SerializedName("message") var message: String?
) {
    constructor(errorResponse: ErrorResponse?) : this(errorResponse?.error, errorResponse?.errorCode, errorResponse?.message)
}