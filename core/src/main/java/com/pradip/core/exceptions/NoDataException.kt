package com.pradip.core.exceptions

/**
 * Indicates that we could not find some requested data.
 */
data class NoDataException(val msg: String = "") : Throwable(msg)
