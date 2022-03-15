package com.zemoga.zemogatest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zemoga.zemogatest.data.model.FavoritePost
import com.zemoga.zemogatest.data.model.PostDataModel


@Database(entities = [FavoritePost::class, PostDataModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabasenIstance(mContext: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabaseInstance(mContext).also {
                    INSTANCE = it
                }
            }


        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()

    }

}