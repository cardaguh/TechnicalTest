package com.zemoga.zemogatest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

class PostResponse(
  //  val info: Pager,
    val results: ArrayList<PostDataModel>
)

data class Pager(
    val count: Long,
    val pages: Int,
    val next: String?,
    val prev: String?
)





data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)