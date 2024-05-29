package com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend

import com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon.GetListPokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServicesBaseProject {
    //API LIST
    //############

    //Test Pokemon
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<GetListPokemonResponse>
}