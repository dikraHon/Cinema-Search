package com.example.cinemasearch.data.database.daoPackage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cinemasearch.domain.modelData.Films

@Dao
interface FilmsDao {
    @Query("SELECT * FROM films")
    suspend fun getAllFilms(): List<Films>

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    suspend fun getFavoriteFilms(): List<Films>
    @Query("SELECT EXISTS(SELECT * FROM films WHERE id = :filmId AND isFavorite = 1)")
    suspend fun isFavorite(filmId: Long): Boolean
    @Query("SELECT id FROM films WHERE id = :filmId")
    suspend fun checkFilmExists(filmId: Long): Long?

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Long): Films?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: Films)

    @Update(entity = Films::class)
    suspend fun updateFilm(film: Films)

    @Query("DELETE FROM films WHERE isFavorite = 0 AND lastUpdated < :expiryTime")
    suspend fun clearOldNonFavorites(expiryTime: Long)
}