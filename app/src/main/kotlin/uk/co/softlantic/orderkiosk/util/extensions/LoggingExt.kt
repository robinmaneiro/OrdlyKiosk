package uk.co.softlantic.orderkiosk.util.extensions

import timber.log.Timber

inline fun infoLog(message: () -> String) = Timber.i(message())
inline fun debugLog(throwable: Throwable? = null, message: () -> String) = Timber.i(throwable, message())
inline fun errorLog(throwable: Throwable? = null, message: () -> String) = Timber.e(throwable, message())
