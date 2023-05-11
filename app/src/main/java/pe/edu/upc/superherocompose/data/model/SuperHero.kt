package pe.edu.upc.superherocompose.data.model

import com.google.gson.annotations.SerializedName

data class SuperHero(
    val id: String,
    val name: String,
    val biography: Biography,
    val image: SuperHeroImage,
    var favorite: Boolean
)

data class Biography(

    @SerializedName("full-name")
    val fullName: String
)

data class SuperHeroImage(
    val url: String
)