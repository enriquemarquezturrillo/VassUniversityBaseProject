package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.data.domain.model.testpokemon.GetListPokemonModel
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface DataSourceBaseProject {

    //Test Pokemon
    fun getListPokemon(limit: Int, offset: Int): Flow<BaseResponse<GetListPokemonModel>>

    //Users List
    fun getUsersListFlow(): Flow<ArrayList<UserModel>>

}