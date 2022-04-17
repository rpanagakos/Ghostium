package com.rdp.ghostium.models.errors

import com.rdp.ghostium.models.errors.mapper.ErrorMapper
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): com.rdp.ghostium.models.errors.mapper.Error {
        return com.rdp.ghostium.models.errors.mapper.Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}