package com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetListPokemonResultModel(
    val name: String = "",
    val url: String = ""
) : BaseModel()