package com.github.joseserize0222.cognifirepluginapplication

import com.github.joseserize0222.cognifirepluginapplication.services.FileAnalyzerService
import com.github.joseserize0222.cognifirepluginapplication.utils.KotlinFileStats
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {

    private lateinit var fileAnalyzerService: FileAnalyzerService

    override fun setUp() {
        super.setUp()
        fileAnalyzerService = project.service<FileAnalyzerService>()
    }

    fun testNoFunction() {
        val file = myFixture.configureByFile("NoFunction.kt").virtualFile
        val stats = analyzeFile(file)
        assertEquals(10, stats.totalLines)
        assertEquals(0, stats.todoLines)
        assertEquals("None", stats.getFunctionName())
    }

    fun testNotNamedFunction() {
        val file = myFixture.configureByFile("NotNamedFunction.kt").virtualFile
        val stats = analyzeFile(file)
        assertEquals(1, stats.totalLines)
        assertEquals(0, stats.todoLines)
        assertEquals("None", stats.getFunctionName())
    }

    fun testFunction() {
        val file = myFixture.configureByFile("Function.kt").virtualFile
        val stats = analyzeFile(file)
        assertEquals(34, stats.totalLines)
        assertEquals(0, stats.todoLines)
        assertEquals("getBst", stats.getFunctionName())
    }

    fun testInnerFunction() {
        val file = myFixture.configureByFile("InnerFunction.kt").virtualFile
        val stats = analyzeFile(file)
        assertEquals(24, stats.totalLines)
        assertEquals(0, stats.todoLines)
        assertEquals("innerFunction", stats.getFunctionName())
    }

    fun testTODOLines() {
        val file = myFixture.configureByFile("TODOLines.kt").virtualFile
        val stats = analyzeFile(file)
        assertEquals(6, stats.totalLines)
        assertEquals(4, stats.todoLines)
        assertEquals("None", stats.getFunctionName())
    }

    private fun analyzeFile(file: VirtualFile) : KotlinFileStats {
        return fileAnalyzerService.calculateStats(file)
    }

    override fun getTestDataPath() = "src/test/testData/"
}
