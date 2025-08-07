package com.example.cinemasearch.data.database.daoPackage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemasearch.domain.modelData.Films
@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritesFilms(films: Films)

    @Query("DELETE FROM films WHERE id = :filmId")
    suspend fun deleteFavoriteFilms(filmId: Long)

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    suspend fun getFavoriteFilms(): List<Films>
}