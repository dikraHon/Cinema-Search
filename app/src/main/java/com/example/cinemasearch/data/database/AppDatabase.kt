package com.example.cinemasearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinemasearch.domain.CollectionFilms
import com.example.cinemasearch.domain.FilmCollectionCrossRef
import com.example.cinemasearch.domain.Films

@Database(
    entities = [
        Films::class,
        CollectionFilms::class,
        FilmCollectionCrossRef::class
               ],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao
    abstract fun collectionsDao(): CollectionsDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "films_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                Instance = instance
                instance
            }
        }
    }
}