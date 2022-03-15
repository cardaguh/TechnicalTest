package com.zemoga.zemogatest.domain

import android.content.Context
import androidx.room.Room
import com.zemoga.zemogatest.data.*
import com.zemoga.zemogatest.data.model.CommentModel
import com.zemoga.zemogatest.data.model.FavoritePost
import retrofit2.Response
import timber.log.Timber

class DetailsRepository(context: Context) {
    private var apiService: WebService? = null
    private var db: AppDatabase
    private var favoritesDao: FavoritesDao
    private var postDao: PostDao


    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
         db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        ).build()
        favoritesDao = db.favoritesDao()
        postDao = db.postDao()
    }


    suspend fun getComments(postId: Long): Response<List<CommentModel>>? {
        return try {
            apiService?.getComments(postId)
        } catch (exception: Exception) {
            Timber.tag("getData").e(exception)
            null
        }
    }



    suspend fun addToFavorite(id: Long, userID: Long,  title: String, body: String){
        favoritesDao.insert(FavoritePost(id,userID,title,body ))
    }

    suspend fun isFavorite(id: Long): Boolean{
        if(favoritesDao.isRecordExists(id) != null){
            return true
        }
        return false
    }

    suspend fun deletePost(id: Long){
        favoritesDao.deleteSingleRecord(id)
    }
    suspend fun deleteFromPosts(id: Long){
        postDao.deleteSingleRecord(id)
    }

}