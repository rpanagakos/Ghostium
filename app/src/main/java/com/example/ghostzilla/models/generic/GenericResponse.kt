package com.example.ghostzilla.models.generic

data class GenericResponse<T>(
    val data: T? = null
) {}