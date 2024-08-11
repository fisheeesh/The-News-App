package com.swamyiphyo.thenewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * A class that represents as database table.
 * Source itself is object which it is not supported by database so
 * we have to convert it into data type which is supported by database.
 * To do that we have to serialize the whole data class.
 */
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val author: String,
    val content: Any,
    val description: Any,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: Any
) : Serializable
/**
 * Serialization is the process of converting an object to format that can be easily formatted or transmitted
 */