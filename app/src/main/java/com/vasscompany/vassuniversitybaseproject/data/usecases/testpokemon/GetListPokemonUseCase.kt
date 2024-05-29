package com.vasscompany.vassuniversitybaseproject.data.usecases.testpokemon

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend.DataProviderBaseProject
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(private val dataProviderBaseProject: DataProviderBaseProject) {
    operator fun invoke(limit: Int, offset: Int): Flow<BaseResponse<GetListPokemonModel>> {
        return dataProviderBaseProject.getListPokemon(limit, offset)
    }
}