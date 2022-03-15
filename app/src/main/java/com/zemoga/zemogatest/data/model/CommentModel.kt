package com.zemoga.zemogatest.data.model

data class CommentModel(
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String,
)
