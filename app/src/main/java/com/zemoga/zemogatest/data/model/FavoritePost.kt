package com.zemoga.zemogatest.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_post" )
data class FavoritePost constructor(
    @field:SerializedName("id") @PrimaryKey @ColumnInfo(name = "id") var id: Long,
    @field:SerializedName("user_id")  @ColumnInfo(name = "user_id") var userId: Long,
    @field:SerializedName("title") @ColumnInfo(name = "title") var title: String?,
    @field:SerializedName("body") @ColumnInfo(name = "body") var body: String?
) : Parcelable