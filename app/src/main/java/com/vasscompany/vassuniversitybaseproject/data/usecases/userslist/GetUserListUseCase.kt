package com.vasscompany.vassuniversitybaseproject.data.usecases.userslist

import com.vasscompany.vassuniversitybaseproject.data.domain.model.users.UserModel
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend.DataProviderBaseProject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val dataProviderBaseProject: DataProviderBaseProject) {
    fun getUsersByFlow(): Flow<ArrayList<UserModel>> {
        return dataProviderBaseProject.getUsersListFlow()
    }

    fun addUserByFlow(userModel: UserModel): Flow<Boolean> = dataProviderBaseProject.addUserFlow(userModel)
}
