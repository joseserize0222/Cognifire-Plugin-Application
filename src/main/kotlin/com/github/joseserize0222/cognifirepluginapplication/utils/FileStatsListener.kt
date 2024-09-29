package com.github.joseserize0222.cognifirepluginapplication.utils

interface FileStatsListener {
    fun callback(allStats: KotlinFileStats) {}
}