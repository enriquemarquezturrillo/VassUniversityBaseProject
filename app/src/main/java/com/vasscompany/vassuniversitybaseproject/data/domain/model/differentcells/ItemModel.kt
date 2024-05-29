package com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel

class ItemModel(
    var name: String,
    var weight: Int,
    var image: String,
    var expand: Boolean,
) : BaseModel()