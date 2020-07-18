package de.pottcode.jokegenerator.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RandomJoke(

    @field:SerializedName("icon_url")
    val iconUrl: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("value")
    val joke: String,

    @field:SerializedName("url")
    val url: String,

    var timeStamp: String
)
