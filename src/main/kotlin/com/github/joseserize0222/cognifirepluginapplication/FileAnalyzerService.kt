package com.github.joseserize0222.cognifirepluginapplication

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtFunction

@Service(Service.Level.PROJECT)
class FileAnalyzerService(private val project: Project) : Disposable {
    private val psiManager: PsiManager = PsiManager.getInstance(project)
    private val listeners = mutableListOf<FileStatsListener>()

    init {
        setupListeners()
    }

    private fun calculateStats(file: VirtualFile) : KotlinFileStats {
        val ktFile = psiManager.findFile(file) ?:  return KotlinFileStats(0, 0, null, "None")
        val lines = ktFile.text.lines()
        val totalLines = lines.size
        val totalTODOLines = lines.count { it.contains("TODO") }
        var longestFunction: KtFunction? = null
        var maxFunctionLines = 0

        val functions = PsiTreeUtil.findChildrenOfType(ktFile, KtFunction::class.java)
        for (function in functions) {
            if (function.text.lines().size > maxFunctionLines) {
                maxFunctionLines = function.text.lines().size
                longestFunction = function
            }
        }

        return KotlinFileStats(totalLines, totalTODOLines, longestFunction, file.name)
    }

    private fun setupListeners() {
        EditorFactory.getInstance().eventMulticaster.addDocumentListener(object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                PsiDocumentManager.getInstance(project).commitDocument(event.document)
                updateStats()
            }
        }, this)
        project.messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, object :
            FileEditorManagerListener {
            override fun selectionChanged(event: FileEditorManagerEvent) {
                updateStats()
            }
        })
    }

    fun addListener(listener: FileStatsListener) {
        listeners.add(listener)
        updateStats()
    }

    fun updateStats() {
        val virtualFile = FileEditorManager.getInstance(project).selectedFiles.firstOrNull() ?: return
        val allStats = calculateStats(virtualFile)
        for (listener in listeners) {
            listener.callback(allStats)
        }
    }

    override fun dispose() {}
}