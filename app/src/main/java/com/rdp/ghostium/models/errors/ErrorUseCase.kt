package com.rdp.ghostium.models.errors

interface ErrorUseCase {
    fun getError(errorCode: Int): com.rdp.ghostium.models.errors.mapper.Error
}