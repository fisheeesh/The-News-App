package com.swamyiphyo.thenewsapp.database

import androidx.room.TypeConverters
import com.swamyiphyo.thenewsapp.models.Source

/**
 * Type Converters is used to convert complex data types to format data types which are supported by database
 */
class Converters {

    /**
     * This function will be used when storing a source obj in the database
     */
    @TypeConverters
    fun fromSource(source : Source) : String = source.name

    /**
     * This function will be used when retrieving a source obj from the database
     */
    @TypeConverters
    fun toSource(name : String) : Source = Source(name, name)
}