package pe.edu.upc.superherocompose.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import pe.edu.upc.superherocompose.data.local.SuperHeroDao
import pe.edu.upc.superherocompose.data.local.SuperHeroEntity
import pe.edu.upc.superherocompose.data.model.Biography
import pe.edu.upc.superherocompose.data.model.SuperHero
import pe.edu.upc.superherocompose.data.model.SuperHeroImage
import pe.edu.upc.superherocompose.data.remote.SuperHeroDetail
import pe.edu.upc.superherocompose.data.remote.SuperHeroResponse
import pe.edu.upc.superherocompose.data.remote.SuperHeroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuperHeroRepository(
    private val superHeroService: SuperHeroService,
    private val superHeroDao: SuperHeroDao

) {
    private val _superHeroes = MutableLiveData<List<SuperHero>>(emptyList())
    val superHeroes get() = _superHeroes

    private val _favoriteHeroes = MutableLiveData<List<SuperHero>>(emptyList())
    val favoriteHeroes get() = _favoriteHeroes

    fun fetchByName(name: String) {
        val fetchByName = superHeroService.fetchByName(name)

        fetchByName.enqueue(object : Callback<SuperHeroResponse> {
            override fun onResponse(
                call: Call<SuperHeroResponse>,
                response: Response<SuperHeroResponse>
            ) {
                if (response.isSuccessful) {

                    if (response.body()!!.results == null) {
                        _superHeroes.value = emptyList()
                    } else {
                        _superHeroes.value = response.body()!!.results!!
                        for (superHero in _superHeroes.value!!) {
                            superHero.favorite = superHeroDao.fetchById(superHero.id).isNotEmpty()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SuperHeroResponse>, t: Throwable) {
                Log.d("SuperHeroRepository", t.message.toString())
            }

        })
    }
    private fun fetchById(id: String): SuperHero {
        var superHero: SuperHero? = null
        var justURL = "https://cdn.waifu.im/7270.png"
        var justName = MutableLiveData<String>()
        justName.value = "uwu"

        val fetchById = superHeroService.fetchById(id = id)
        fetchById.enqueue(object : Callback<SuperHeroDetail> {
            override fun onResponse(
                call: Call<SuperHeroDetail>,
                response: Response<SuperHeroDetail>
            ) {
                if (response.isSuccessful) {
                    justName.value = response.body()!!.name
                }
            }
            override fun onFailure(call: Call<SuperHeroDetail>, t: Throwable) {
                Log.d("SuperHeroRepository", t.message.toString())
            }
        })
        superHero = SuperHero(id,
            justName.value.toString(),
            Biography("No encontrado"),
            SuperHeroImage(justURL),
            true)
        return superHero!!
    }

    fun fetchFavorites() {
        val superHeroEntities = superHeroDao.fetchAll()
        val superHeroes2 = mutableListOf<SuperHero>()
        for (superHeroEntity in superHeroEntities) {
            superHeroes2.add(fetchById(superHeroEntity.id)!!)
        }
        _favoriteHeroes.value = superHeroes2
    }

    fun insert(superHero: SuperHero) {
        val superHeroEntity = SuperHeroEntity(superHero.id)
        superHeroDao.insert(superHeroEntity)
        superHero.favorite = true
    }

    fun delete(superHero: SuperHero) {
        val superHeroEntity = SuperHeroEntity(superHero.id)
        superHeroDao.delete(superHeroEntity)
        superHero.favorite = false
    }
}