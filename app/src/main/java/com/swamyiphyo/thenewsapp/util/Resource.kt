package com.swamyiphyo.thenewsapp.util

/**
 * Sealed class that often restricts the hierarchy of classes and provides a way to define.
 * In here, it represents the difference states that can occur when fetching data from remote source.
 * Different States as success or error states which we will be using in fragments and view model.
 */
sealed class Resource<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Success<T>(data : T) : Resource<T>(data)
    class Error<T>(message : String, data : T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}