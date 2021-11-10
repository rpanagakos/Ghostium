package com.example.ghostzilla.models.generic

import com.bumptech.glide.load.engine.Resource

// A generic class that contains data and status about loading this data.
sealed class Result<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class DataError<T>(errorCode: Int) : Result<T>(null, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
        }
    }
}