package pe.edu.upc.superherocompose.ui.screens.superheroes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.upc.superherocompose.data.local.AppDatabase
import pe.edu.upc.superherocompose.data.model.SuperHero
import pe.edu.upc.superherocompose.data.remote.SuperHeroClient
import pe.edu.upc.superherocompose.repository.SuperHeroRepository

class SuperHeroViewModel(application: Application) : AndroidViewModel(application) {

    private val superHeroService = SuperHeroClient.superHeroService()
    private val superHeroDao = AppDatabase.getInstance(application).superHeroDao()
    private val superHeroRepository = SuperHeroRepository(superHeroService, superHeroDao)

    private var _superHeroes = superHeroRepository.superHeroes
    val superHeroes get() = _superHeroes

    private var _favoriteHeroes = superHeroRepository.favoriteHeroes
    val favoriteHeroes get() = _favoriteHeroes

    private var _name = MutableLiveData<String>()
    val name get() = _name

    fun update(name: String) {
        _name.value = name
    }

    fun fetchByName() {
        superHeroRepository.fetchByName(name.value!!)
        _superHeroes.value = superHeroRepository.superHeroes.value
    }

    fun fetchFavorites() {
        superHeroRepository.fetchFavorites()
        _favoriteHeroes.value = superHeroRepository.favoriteHeroes.value
    }

    fun insert(superHero: SuperHero) {
        superHeroRepository.insert(superHero)
    }

    fun delete(superHero: SuperHero) {
        superHeroRepository.delete(superHero)
    }
}