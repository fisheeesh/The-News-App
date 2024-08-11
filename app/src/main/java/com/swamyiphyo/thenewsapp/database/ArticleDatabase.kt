package com.swamyiphyo.thenewsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swamyiphyo.thenewsapp.models.Article

/**
 * An abstract class that serves as the main access point to the underlying SQLite database,
 * allowing for the creation and management of the database schema.
 */
@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    /**
     * which will declare the instance of article dao interface
     */
    abstract fun getArticleDao() : ArticleDao

    companion object{
        /**
         * Volatile ensures that the instance variable is always up-to-date and
         * changes made by one thread are immediately visible to other threads.
         */
        @Volatile
        private var instance : ArticleDatabase? = null
        /**
         * LOCK object is used to synchronize access to the database creation process.
         * It ensures that only one thread can execute the code inside the synchronized block at a time.
         */
        private val LOCK = Any()

        /**
         * Custom invoke operator for companion object which allows us to create
         * the instance of the article database by calling article database context.
         * It follows Singleton pattern ensures that only one instance of the database is created.
         * Invoke operator is used for simplicity when creating an instance and also double check
         * locking pattern is implement to ensure thread safety during database creation process.
         */
        operator fun invoke(context: Context) =
            /**
             *This line checks if the instance is already initialized.
             * If not then, it enters the synchronized block using LOCK object
             * ensures only one thread can create the database instance at a time.
             */
            instance ?: synchronized(LOCK){
                /**
                 * inside the block, if the instance still null, it creates a new database instance
                 * by using createDatabase function.
                 */
            instance ?:
            createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}