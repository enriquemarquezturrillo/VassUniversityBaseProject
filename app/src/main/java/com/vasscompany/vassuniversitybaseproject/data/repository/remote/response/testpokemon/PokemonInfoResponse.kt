package com.vasscompany.vassuniversitybaseproject.data.repository.remote.response.testpokemon


import com.google.gson.annotations.SerializedName

data class PokemonInfoResponse(
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sprites")
    val pokemonInfoSpritesResponse: PokemonInfoSpritesResponse?,
    @SerializedName("weight")
    val weight: Int?
)