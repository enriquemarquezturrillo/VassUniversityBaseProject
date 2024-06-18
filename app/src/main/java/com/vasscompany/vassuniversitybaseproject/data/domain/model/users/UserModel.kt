package com.vasscompany.vassuniversitybaseproject.data.domain.model.users

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String = "",
    val years: Int = 0,
    val balance: Double? = null
) : BaseModel()