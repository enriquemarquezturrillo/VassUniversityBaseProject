package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper.testpokemon.GetListPokemonMapper
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceBaseProject @Inject constructor(private val callApiServiceBaseProject: CallApiServiceBaseProject) :
    BaseService(), DataSourceBaseProject {

    //Test Pokemon
    override fun getListPokemon(limit: Int, offset: Int): Flow<BaseResponse<GetListPokemonModel>> = flow {
        val apiResult = callApiServiceBaseProject.callGetListPokemon(limit, offset)
        if (apiResult is BaseResponse.Success) {
            emit(BaseResponse.Success(GetListPokemonMapper().fromResponse(apiResult.data)))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }
}