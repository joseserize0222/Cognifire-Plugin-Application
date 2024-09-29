package com.github.joseserize0222.cognifirepluginapplication

interface FileStatsListener {
    fun callback(allStats: KotlinFileStats) {}
}