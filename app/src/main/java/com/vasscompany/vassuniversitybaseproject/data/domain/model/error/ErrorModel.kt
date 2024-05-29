package com.vasscompany.vassuniversitybaseproject.data.domain.model.error

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel

class ErrorModel(
    var error: String = "unknow",
    var errorCode: String = "",
    var message: String = "unknow"
) : BaseModel()