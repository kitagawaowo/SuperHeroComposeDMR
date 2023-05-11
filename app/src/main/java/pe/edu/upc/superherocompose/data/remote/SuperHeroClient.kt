package pe.edu.upc.superherocompose.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SuperHeroClient {
    private const val BASE_URL = "https://superheroapi.com/api/10157703717092094/"

    fun superHeroService(): SuperHeroService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SuperHeroService::class.java)
    }
}