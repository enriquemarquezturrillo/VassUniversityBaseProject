package com.vasscompany.vassuniversitybaseproject.data.repository.remote.mapper

interface RequestMapper<M, E> {
    fun toRequest(model: M): E
}