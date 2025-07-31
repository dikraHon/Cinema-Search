package com.example.cinemasearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinemasearch.domain.Films

@Database(
    entities = [Films::class],
    version = 3,  // Увеличьте на 1 от предыдущей версии
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao

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
                    .fallbackToDestructiveMigration(false) // Разрушительная миграция (удалит старую БД)
                    // ИЛИ .addMigrations(MIGRATION_1_2) для кастомной миграции
                    .build()
                Instance = instance
                instance
            }
        }
    }
}