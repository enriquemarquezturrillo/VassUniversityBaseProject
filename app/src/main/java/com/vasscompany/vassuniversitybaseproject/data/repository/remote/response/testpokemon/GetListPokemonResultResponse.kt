package com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon

import com.google.gson.annotations.SerializedName

data class GetListPokemonResultResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)