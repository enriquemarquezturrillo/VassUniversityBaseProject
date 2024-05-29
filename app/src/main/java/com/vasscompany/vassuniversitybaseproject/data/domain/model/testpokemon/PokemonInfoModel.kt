package com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon


import com.vasscompany.vassuniversitybaseproject.data.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonInfoModel(
    val baseExperience: Int = -1,
    val height: Int = -1,
    val id: Int = -1,
    val name: String = "",
    val pokemonInfoSpritesModel: PokemonInfoSpritesModel = PokemonInfoSpritesModel(),
    val weight: Int = -1
) : BaseModel()