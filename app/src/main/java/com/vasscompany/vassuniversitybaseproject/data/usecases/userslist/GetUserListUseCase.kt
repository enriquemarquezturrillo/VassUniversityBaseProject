package com.vasscompany.vassuniversitybaseproject.data.usecases.userslist

import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend.DataProviderBaseProject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val dataProviderBaseProject: DataProviderBaseProject) {
    operator fun invoke(): Flow<ArrayList<UserModel>> {
        return dataProviderBaseProject.getUsersList()
    }
}