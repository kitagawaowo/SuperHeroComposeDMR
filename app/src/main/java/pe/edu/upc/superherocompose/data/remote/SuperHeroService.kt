package pe.edu.upc.superherocompose.data.remote

import pe.edu.upc.superherocompose.data.model.SuperHero
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroService {

    @GET("search/{name}")
    fun fetchByName(@Path("name") name: String): Call<SuperHeroResponse>
    @GET("{id}")
    fun fetchById(@Path("id") id: String): Call<SuperHeroDetail>
}