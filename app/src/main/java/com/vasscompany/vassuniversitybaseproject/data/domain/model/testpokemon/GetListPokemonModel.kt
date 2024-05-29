package com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListPokemonModel(
    val count: Int = -1,
    val next: String = "",
    val previous: String = "",
    val results: List<GetListPokemonResultModel> = arrayListOf()
) : BaseModel()