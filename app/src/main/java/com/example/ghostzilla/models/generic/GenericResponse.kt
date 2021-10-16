package com.example.ghostzilla.models.generic

data class GenericResponse<T>(
    val data: T? = null,
    val error : Boolean = false,
    val error_message : String? = null,
    val error_code: Int? = null
) {}