package com.zemoga.zemogatest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostDataModel constructor(
    @ColumnInfo(name = "user_id") val userId: Long,
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title")val title: String = "",
    @ColumnInfo(name = "body")val body: String,
//    val species: String,
//    val type: String,
//    val gender: String,
//    val origin: Origin,
//    val location: Location,
//    val image: String,
//    val episode: MutableList<String>,
//    val url: String
)