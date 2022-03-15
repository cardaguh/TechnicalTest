package com.zemoga.zemogatest.data

import android.icu.text.Replaceable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.zemoga.zemogatest.data.model.FavoritePost


@Dao
interface FavoritesDao {
    @Query("SELECT * from favorite_post")
    suspend fun getAllRecords():MutableList<FavoritePost>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(insert: FavoritePost?)

    @Query("DELETE FROM favorite_post")
    suspend fun deleteAll()

    @Query("SELECT * from favorite_post where id=:id")
    suspend fun isRecordExists(id: Long?): FavoritePost?


    @Query("DELETE FROM favorite_post where id=:id")
    suspend fun deleteSingleRecord(id: Long?)

}