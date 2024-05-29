package com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells

class ListRowTypeTitleModel(val title: String) : ListRowsRecyclerviewModel() {
    override fun getListRowType(): ListRowsTypes {
        return ListRowsRecyclerviewModel.ListRowsTypes.TYPE_TITLE
    }
}