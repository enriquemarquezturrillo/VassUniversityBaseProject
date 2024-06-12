package com.vasscompany.vassuniversitybaseproject.data.repository.remote.memory

import android.util.Log
import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersManager @Inject constructor() {

    val users: ArrayList<UserModel> = arrayListOf()

    init {
        Log.d(TAG, "l> init")
        baseList()
    }

    private fun baseList() {
        users.clear()
        users.add(UserModel("Manolo", 72))
        users.add(UserModel("Alicia", 65))
        users.add(UserModel("Juan", 23))
        users.add(UserModel("Silvia", 7))
        users.add(UserModel("Esther", 24))
        Log.d(TAG, "l> añadidos elementos a la lista: ${users.size}")
    }


    fun getUsersFlow(): Flow<ArrayList<UserModel>> {
        Log.d(TAG, "l> hacemos un return del flow con ${users.size} elementos")
        return flow { emit(users) }
    }


    fun getUsersList(): ArrayList<UserModel> {
        Log.d(TAG, "l> hacemos un return del flow con ${users.size} elementos")
        return users
    }
}