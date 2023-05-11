package pe.edu.upc.superherocompose.data.remote

import pe.edu.upc.superherocompose.data.model.Biography
import pe.edu.upc.superherocompose.data.model.SuperHero
import pe.edu.upc.superherocompose.data.model.SuperHeroImage

data class SuperHeroResponse(
    val results: List<SuperHero>?
)

data class SuperHeroDetail(
    val id: String,
    val name: String,
    val biography: Biography,
    val image: SuperHeroImage,
    var favorite: Boolean
)
