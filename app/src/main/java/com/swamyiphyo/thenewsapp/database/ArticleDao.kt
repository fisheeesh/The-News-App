package com.swamyiphyo.thenewsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swamyiphyo.thenewsapp.models.Article

/**
 * An interface that provides abstract methods for interacting with the database.
 * Acts as bridge between app's code and underlying database and enabling CRUD operations on database entities.
 */
@Dao
interface ArticleDao {
    /**
     * /OnConflictStrategy.REPLACE/
     * A conflict like if same primary key already exits in the table,
     * the old data will be replaced with the new data.
     * This is a safe way of writing code so that we dun face any errors in the future.
     * Suspend functions should be called only from coroutines.
     * Suspend wil make sure that the database operations will be performed on a background thread.
     * Long means the primary key of the article.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article) : Long

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<Article>>
}