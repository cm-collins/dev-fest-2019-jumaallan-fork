package com.androidstudy.devfest19.utils

import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import timber.log.Timber

private class TimberLogger(level: Level = Level.INFO) : Logger(level) {

    override fun display(level: Level, msg: String) {
        if (this.level <= level) {
            when (level) {
                Level.DEBUG -> Timber.tag("KOIN").d(msg)
                Level.INFO -> Timber.tag("KOIN").i(msg)
                Level.ERROR -> Timber.tag("KOIN").e(msg)
                Level.WARNING -> Timber.tag("KOIN").w(msg)
                Level.NONE -> { /* No logging */ }
            }
        }
    }

}

fun KoinApplication.timberLogger(level: Level = Level.INFO): KoinApplication {
    logger(TimberLogger(level))
    return this
}