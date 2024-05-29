package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonResultModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.ResponseMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.GetListPokemonResultResponse

class GetListPokemonResultMapper : ResponseMapper<GetListPokemonResultResponse, GetListPokemonResultModel> {
    override fun fromResponse(response: GetListPokemonResultResponse): GetListPokemonResultModel {
        return GetListPokemonResultModel(response.name ?: "", response.url ?: "")
    }
}