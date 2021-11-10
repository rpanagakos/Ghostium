package com.example.ghostzilla.models.errors

import com.example.ghostzilla.models.errors.mapper.ErrorMapper
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): com.example.ghostzilla.models.errors.mapper.Error {
        return com.example.ghostzilla.models.errors.mapper.Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}