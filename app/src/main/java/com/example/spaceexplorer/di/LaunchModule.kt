package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.database.launch.LaunchDao
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.repository.LaunchDetailRepositoryImpl
import com.example.spaceexplorer.data.repository.LaunchRepositoryImpl
import com.example.spaceexplorer.domain.repository.LaunchDetailRepository
import com.example.spaceexplorer.domain.repository.LaunchRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
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
    fun provideLaunchRepository(
        spaceExplorerApi: SpaceExplorerApi,
        launchDao: LaunchDao,
        settingsRepository: SettingsRepository
    ): LaunchRepository {
        return LaunchRepositoryImpl(
            spaceExplorerApi = spaceExplorerApi,
            launchDao = launchDao,
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLaunchDetailRepository(
        spaceExplorerApi: SpaceExplorerApi,
        launchDao: LaunchDao,
        settingsRepository: SettingsRepository
    ): LaunchDetailRepository {
        return LaunchDetailRepositoryImpl(
            api = spaceExplorerApi,
            launchDao = launchDao,
            settingsRepository = settingsRepository
        )
    }
}