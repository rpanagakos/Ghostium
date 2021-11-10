package com.example.ghostzilla.models.errors

interface ErrorUseCase {
    fun getError(errorCode: Int): com.example.ghostzilla.models.errors.mapper.Error
}