package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.PokemonInfoModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.PokemonInfoSpritesModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.ResponseMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.PokemonInfoResponse


class PokemonInfoMapper : ResponseMapper<PokemonInfoResponse, PokemonInfoModel> {
    override fun fromResponse(response: PokemonInfoResponse): PokemonInfoModel {

        val pokemonInfoSpritesModel = if (response.pokemonInfoSpritesResponse != null) {
            PokemonInfoSpritesMapper().fromResponse(response.pokemonInfoSpritesResponse)
        } else {
            PokemonInfoSpritesModel()
        }

        return PokemonInfoModel(
            response.baseExperience ?: -1,
            response.height ?: -1,
            response.id ?: -1,
            response.name ?: "",
            pokemonInfoSpritesModel,
            response.weight ?: -1
        )
    }
}