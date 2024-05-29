package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.PokemonInfoSpritesModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.ResponseMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.PokemonInfoSpritesResponse


class PokemonInfoSpritesMapper : ResponseMapper<PokemonInfoSpritesResponse, PokemonInfoSpritesModel> {
    override fun fromResponse(response: PokemonInfoSpritesResponse): PokemonInfoSpritesModel {
        return PokemonInfoSpritesModel(
            response.backDefault ?: "",
            response.backFemale ?: "",
            response.backShiny ?: "",
            response.backShinyFemale ?: "",
            response.frontDefault ?: "",
            response.frontFemale ?: "",
            response.frontShiny ?: "",
            response.frontShinyFemale ?: ""
        )
    }
}