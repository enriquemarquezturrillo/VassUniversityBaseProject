package com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel

abstract class ListRowsRecyclerviewModel() : BaseModel() {

    enum class ListRowsTypes {
        TYPE_TITLE, TYPE_ITEM, TYPE_END
    }

    abstract fun getListRowType(): ListRowsTypes
}