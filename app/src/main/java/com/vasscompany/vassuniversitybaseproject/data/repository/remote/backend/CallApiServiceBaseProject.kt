package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.GetListPokemonResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallApiServiceBaseProject @Inject constructor(private val apiServicesBaseProject: ApiServicesBaseProject) : BaseService() {
    //Test Pokemon
    suspend fun callGetListPokemon(limit: Int, offset: Int): BaseResponse<GetListPokemonResponse> {
        return apiCall { apiServicesBaseProject.getListPokemon(limit, offset) }
    }
}