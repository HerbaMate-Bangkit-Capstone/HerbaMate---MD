package com.herbamate.herbamate.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.herbamate.herbamate.data.local.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE id = :id LIMIT 1")
    fun getFavoriteById(id: String): LiveData<Favorite>
}