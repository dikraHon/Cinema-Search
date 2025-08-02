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
    @Query("SELECT * FROM films WHERE isFavorite = 1")
    suspend fun getFavoriteFilms(): List<Films>
    @Update(entity = Films::class)
    suspend fun updateFavoriteStatus(films: Films)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritesFilms(films: Films)
    @Query("DELETE FROM films WHERE id = :filmId AND isFavorite = 1")
    suspend fun deleteFavoriteFilms(filmId: Long)

    @Query("SELECT EXISTS(SELECT * FROM films WHERE id = :filmId AND isFavorite = 1)")
    suspend fun isFavorite(filmId: Long): Boolean

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Long): Films?

    @Query("SELECT id FROM films WHERE id = :filmId")
    suspend fun checkFilmExists(filmId: Long): Long?


}