package com.zemoga.zemogatest.data

import com.zemoga.zemogatest.data.model.CommentModel
import com.zemoga.zemogatest.data.model.PostDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    //Lo que hace falta en la url
    @GET("posts") // Aquí va el end-point
    suspend fun getPosts()
            : Response<List<PostDataModel>>

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postID: Long)
            : Response<List<CommentModel>>
}