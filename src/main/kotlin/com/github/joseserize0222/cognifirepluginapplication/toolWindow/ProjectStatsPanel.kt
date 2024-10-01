package com.github.joseserize0222.cognifirepluginapplication.toolWindow
import com.github.joseserize0222.cognifirepluginapplication.services.FileAnalyzerService
import com.github.joseserize0222.cognifirepluginapplication.utils.FileStatsListener
import com.github.joseserize0222.cognifirepluginapplication.utils.KotlinFileStats
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.ui.EditorTextField
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*

class ProjectStatsPanel(project: Project) : FileStatsListener {
    val content: JComponent
    private val editorField: EditorTextField
    init {
        val panel = JBPanel<JBPanel<*>>(BorderLayout())
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("kt")
        editorField = EditorTextField("", project, fileType).apply {
            isViewer = true
            font = Font("JetBrains Mono", Font.PLAIN, 12)
            setOneLineMode(false)
            setAutoscrolls(true)
        }
        val scrollPane = JBScrollPane(editorField)
        panel.add(scrollPane, BorderLayout.CENTER)
        content = panel
        ApplicationManager.getApplication().messageBus.connect().subscribe(
            LafManagerListener.TOPIC, LafManagerListener {
                updateEditorFieldTheme()
            }
        )
        project.service<FileAnalyzerService>().addListener(this)
    }

    private fun updateEditorFieldTheme() {
        val scheme = EditorColorsManager.getInstance().globalScheme
        editorField.background = scheme.defaultBackground
        editorField.foreground = scheme.defaultForeground
    }

    override fun callback(allStats: KotlinFileStats) {
        val stats = buildString {
            append("Kotlin File Stats for ${allStats.fileName}\n\n")
            append("All Lines: ${allStats.totalLines}\n")
            append("TODO Lines: ${allStats.todoLines}\n")
            append("Longest Function: ${allStats.getFunctionName()} with ${allStats.getFunctionLines()} ${ if (allStats.getFunctionLines() == 1) "line" else "lines"}\n")
            append("Body Expression:\n")
            append(allStats.getFunctionContent())
        }
        SwingUtilities.invokeLater {
            editorField.text = stats
        }
    }
}