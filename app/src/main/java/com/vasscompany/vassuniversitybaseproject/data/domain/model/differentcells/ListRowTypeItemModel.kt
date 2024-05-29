package com.vasscompany.vassuniversitybaseproject.data.domain.model.differentcells

class ListRowTypeItemModel(val item: ItemModel) : ListRowsRecyclerviewModel() {
    override fun getListRowType(): ListRowsTypes {
        return ListRowsRecyclerviewModel.ListRowsTypes.TYPE_ITEM
    }

    override fun toString(): String {
        return "name: ${item.name}, weight: ${item.weight}, image: ${item.image}, expand: ${item.expand}"
    }
}