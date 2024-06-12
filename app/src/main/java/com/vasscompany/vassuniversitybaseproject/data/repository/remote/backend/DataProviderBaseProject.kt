package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.memory.UsersManager
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProviderBaseProject @Inject constructor(
    private val remoteDataSourceKigii: RemoteDataSourceBaseProject,
    private val usersManager: UsersManager
) : DataSourceBaseProject {
    //Test Pokemon
    override fun getListPokemon(limit: Int, offset: Int): Flow<BaseResponse<GetListPokemonModel>> {
        return remoteDataSourceKigii.getListPokemon(limit, offset)
    }

    override fun getUsersListFlow(): Flow<ArrayList<UserModel>> {
        return usersManager.getUsersFlow()
    }
}