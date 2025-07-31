package com.example.cinemasearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cinemasearch.domain.Films

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films")
    suspend fun getAllFilms(): List<Films>

    @Query("SELECT * FROM films WHERE id = :filmId") // Было "SELECT* FROM films..."
    suspend fun getFilmById(filmId: Long): Films?

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    suspend fun getFavoriteFilms(): List<Films>

    @Update(entity = Films::class)
    suspend fun updateFavoriteStatus(films: Films)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritesFilms(films: Films)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFilms(films: List<Films>)

    @Query("DELETE FROM films WHERE id = :filmId AND isFavorite = 1")
    suspend fun deleteFavoriteFilms(filmId: Long)

    @Query("DELETE FROM films")
    suspend fun deleteAllFilms()


}