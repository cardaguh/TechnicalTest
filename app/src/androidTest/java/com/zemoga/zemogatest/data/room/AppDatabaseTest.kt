package com.cyclopsapps.kotlinrecyclerviewwithretrofit.data.room

import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.zemoga.zemogatest.data.AppDatabase
import com.zemoga.zemogatest.data.model.FavoritePost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        appDatabase = inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun testSavePosts() {
        CoroutineScope(Dispatchers.Main).launch {
            val post = FavoritePost(
                1,
                1,
                "Post 1",
                "Content of post 1"
            )
            appDatabase.favoritesDao().insert(post)
            val result = appDatabase.favoritesDao().getAllRecords()
            Assert.assertEquals(1, result.size)

            Assert.assertEquals("Post 1", result.firstOrNull()?.title)
        }
    }

    @Test
    fun testDeletePosts() {
        CoroutineScope(Dispatchers.Main).launch {
            val post = FavoritePost(
                1,
                1,
                "Post 1",
                "Content of post 1"
            )
            appDatabase.favoritesDao().insert(post)
            appDatabase.favoritesDao().deleteSingleRecord(post.id)
            val result = appDatabase.favoritesDao().getAllRecords()
            Assert.assertEquals(0, result.size)

            //Assert.assertEquals("abc123", result.firstOrNull()?.photo)
        }
    }

    @Test
    fun testGetAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            val post1 = FavoritePost(
                1,
                1,
                "Post 1",
                "Content of post 1"
            )
            appDatabase.favoritesDao().insert(post1)
            val post2 = FavoritePost(
                2,
                2,
                "Post 2",
                "Content of post 2"
            )
            appDatabase.favoritesDao().insert(post2)
            appDatabase.favoritesDao().getAllRecords()
            val result = appDatabase.favoritesDao().getAllRecords()
            Assert.assertEquals(2, result.size)

            //Assert.assertEquals("abc123", result.firstOrNull()?.photo)
        }
    }

}