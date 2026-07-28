package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.database.FavoritesDao
import com.example.spaceexplorer.data.repository.FavoritesListRepositoryImpl
import com.example.spaceexplorer.domain.repository.FavoritesListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class FavoritesModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFavoritesRepository(dao: FavoritesDao): FavoritesListRepository {
        return FavoritesListRepositoryImpl(dao)
    }
}