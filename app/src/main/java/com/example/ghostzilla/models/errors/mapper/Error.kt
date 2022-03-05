package com.example.ghostzilla.models.errors.mapper


class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(code = DEFAULT_ERROR, description = exception.message
        ?: "")
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val SEARCH_ERROR = -104
const val NOT_FOUND = 404
const val NO_SEARCHES = 1