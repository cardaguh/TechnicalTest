package com.zemoga.zemogatest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zemoga.zemogatest.data.model.FavoritePost
import com.zemoga.zemogatest.data.model.PostDataModel

@Dao
interface PostDao {

    @Query("SELECT * from posts")
    suspend fun getAllPosts():MutableList<PostDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(insert:PostDataModel)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Query("DELETE FROM posts where id=:id")
    suspend fun deleteSingleRecord(id: Long?)
}