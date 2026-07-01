package com.example.spaceexplorer.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    val api: SpaceExplorerApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpaceExplorerApi::class.java)
    }
}