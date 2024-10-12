package com.github.joseserize0222.cognifirepluginapplication.utils

import org.jetbrains.kotlin.psi.KtFunction

data class KotlinFileStats(
    val totalLines: Int,
    val todoLines: Int,
    val function: KtFunction?,
    val fileName: String
) {
    fun getFunctionContent(): String {
        return function?.text?.let {
            val lines = it.lines()
            lines.first() + "\n" + lines.drop(1).joinToString("\n").trimIndent()
        } ?: "None"
    }

    fun getFunctionLines() : Int {
        return function?.text?.lines()?.size ?: 0
    }

    fun getFunctionName() : String {
        return function?.name ?: "None"
    }
}