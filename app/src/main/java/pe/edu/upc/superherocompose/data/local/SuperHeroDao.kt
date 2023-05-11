package pe.edu.upc.superherocompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SuperHeroDao {
    @Query("select * from SuperHero where id=:id")
    fun fetchById(id: String): List<SuperHeroEntity>

    @Query("select * from SuperHero")
    fun fetchAll(): List<SuperHeroEntity>

    @Insert
    fun insert(superHeroEntity: SuperHeroEntity)

    @Delete
    fun delete(superHeroEntity: SuperHeroEntity)
}