package com.pradip.core.exceptions

/**
 * Indicates that we could not find some requested data.
 */
data class ApiException(val msg: String = "", val status: String = "") : Exception(msg)
