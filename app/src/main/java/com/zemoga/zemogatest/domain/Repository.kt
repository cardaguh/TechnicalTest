package com.zemoga.zemogatest.domain
import android.content.Context
import androidx.room.Room
import com.zemoga.zemogatest.data.*
import com.zemoga.zemogatest.data.model.FavoritePost
import com.zemoga.zemogatest.data.model.PostDataModel
import retrofit2.Response
import timber.log.Timber


class PostRepository(context: Context) {

    private var apiService: WebService? = null
    private var db: AppDatabase
    private var postDao: PostDao
    private var favoritesDao: FavoritesDao

    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        ).build()
        postDao = db.postDao()
        favoritesDao = db.favoritesDao()
    }

    /**
     *
     */
    suspend fun getPost(): Response<List<PostDataModel>>? {
        return try {
                apiService?.getPosts()
        } catch (exception: Exception) {
            Timber.tag("getData").e(exception)
            null
        }
    }


    //OTRA FORMA
    suspend fun getCharacter2() = apiService?.getPosts()

    suspend fun insertPost(postDataModel: PostDataModel){
        postDao.insertPost(postDataModel)
    }
    suspend fun getAllPosts(): List<PostDataModel>{
        return postDao.getAllPosts()
    }

    suspend fun getAllFavorites(): List<FavoritePost>{
        return favoritesDao.getAllRecords()
    }

    suspend fun isFavorite(id: Long): Boolean{
        if(favoritesDao.isRecordExists(id) != null){
            return true
        }
        return false
    }

    suspend fun deleteAll(){
        postDao.deleteAll()
    }

    suspend fun deleteAllFromFav(){
        favoritesDao.deleteAll()
    }

}