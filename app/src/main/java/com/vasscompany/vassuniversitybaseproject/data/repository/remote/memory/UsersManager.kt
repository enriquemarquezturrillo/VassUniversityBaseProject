package com.vasscompany.vassuniversitybaseproject.data.repository.remote.memory

import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersManager @Inject constructor() {

    val users: ArrayList<UserModel> = arrayListOf()

    init {
        baseList()
    }

    private fun baseList() {
        users.clear()
        users.add(UserModel("Manolo", 72))
        users.add(UserModel("Alicia", 65))
        users.add(UserModel("Juan", 23))
        users.add(UserModel("Silvia", 7))
        users.add(UserModel("Esther", 24))
    }


    fun getUsers(): Flow<ArrayList<UserModel>> {
        return flow { users }
    }
}