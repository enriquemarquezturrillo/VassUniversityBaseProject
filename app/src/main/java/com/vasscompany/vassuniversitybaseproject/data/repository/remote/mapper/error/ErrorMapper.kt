package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.error

import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.ResponseMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.error.ErrorResponse

class ErrorMapper : ResponseMapper<ErrorResponse, ErrorModel> {
    override fun fromResponse(response: ErrorResponse): ErrorModel {
        return ErrorModel(response.error ?: "", response.errorCode ?: "", response.message ?: "")
    }
}