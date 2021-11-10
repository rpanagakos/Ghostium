package com.example.ghostzilla.models.generic

// A generic class that contains data and status about loading this data.
sealed class GenericResponse<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : GenericResponse<T>(data)
    class Loading<T>(data: T? = null) : GenericResponse<T>(data)
    class DataError<T>(errorCode: Int) : GenericResponse<T>(null, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
        }
    }
}