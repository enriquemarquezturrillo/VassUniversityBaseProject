package com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon


import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonInfoSpritesModel(
    val backDefault: String = "",
    val backFemale: String = "",
    val backShiny: String = "",
    val backShinyFemale: String = "",
    val frontDefault: String = "",
    val frontFemale: String = "",
    val frontShiny: String = "",
    val frontShinyFemale: String = ""
) : BaseModel()