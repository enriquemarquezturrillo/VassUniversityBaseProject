package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonResultModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.ResponseMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.GetListPokemonResponse

class GetListPokemonMapper : ResponseMapper<GetListPokemonResponse, GetListPokemonModel> {
    override fun fromResponse(response: GetListPokemonResponse): GetListPokemonModel {

        val resultModel = arrayListOf<GetListPokemonResultModel>()

        if (!response.results.isNullOrEmpty()) {
            val getListPokemonResultMapper = GetListPokemonResultMapper()
            response.results.forEach { getListPokemonResultResponse ->
                resultModel.add(getListPokemonResultMapper.fromResponse(getListPokemonResultResponse))
            }
        }

        return GetListPokemonModel(
            response.count ?: -1,
            response.next ?: "",
            response.previous ?: "",
            resultModel
        )
    }
}