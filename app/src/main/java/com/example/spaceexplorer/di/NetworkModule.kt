package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true   // don't crash on unknown keys
        coerceInputValues = true  // if key is null, use default value
    }

    @Provides
    @Singleton
    fun provideSpaceExplorerApi(json: Json): SpaceExplorerApi {
        val contentType: MediaType = "application/json".toMediaType()
        val converterFactory: Converter.Factory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/v4/")
            .addConverterFactory(converterFactory)
            .build()
            .create(SpaceExplorerApi::class.java)
    }
}