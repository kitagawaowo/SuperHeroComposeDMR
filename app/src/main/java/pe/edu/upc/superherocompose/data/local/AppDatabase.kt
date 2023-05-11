package pe.edu.upc.superherocompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SuperHeroEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun superHeroDao(): SuperHeroDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "superHero.db"
            )
                .allowMainThreadQueries()
                .build()
            return INSTANCE as AppDatabase
        }
    }
}