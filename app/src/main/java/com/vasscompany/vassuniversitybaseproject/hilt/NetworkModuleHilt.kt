package com.vasscompany.vassuniversitybaseproject.hilt

import com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend.ApiServicesBaseProject
import com.vasscompany.vassuniversitybaseproject.data.repository.remote.backend.RetrofitClientBaseProject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleHilt {
    @Provides
    @Singleton
    fun provideApiServicesBaseProject(retrofitClientBaseProject: RetrofitClientBaseProject): ApiServicesBaseProject {
        return retrofitClientBaseProject.retrofit.create(ApiServicesBaseProject::class.java)
    }

}