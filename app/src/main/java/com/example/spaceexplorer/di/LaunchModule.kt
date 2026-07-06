package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.repository.LaunchRepositoryImpl
import com.example.spaceexplorer.domain.repository.LaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

// TODO ActivityRetainedComponent or SingletonComponent
@Module
@InstallIn(ActivityRetainedComponent::class)
class LaunchModule {

    @Provides
    @ActivityRetainedScoped
    fun provideLaunchRepository(spaceExplorerApi: SpaceExplorerApi): LaunchRepository {
        return LaunchRepositoryImpl(spaceExplorerApi)
    }
}