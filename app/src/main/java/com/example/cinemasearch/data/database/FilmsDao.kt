package com.example.cinemasearch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemasearch.domain.Films

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films")
    suspend fun getAllFilms(): List<Films>

    @Query("SELECT * FROM films WHERE id = :filmId") // Было "SELECT* FROM films..."
    suspend fun getFilmById(filmId: Long): Films?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(films: Films)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFilms(films: List<Films>)

    @Query("DELETE FROM films WHERE id = :filmId")
    suspend fun deleteFilmById(filmId: Long)

    @Query("DELETE FROM films")
    suspend fun deleteAllFilms()


}