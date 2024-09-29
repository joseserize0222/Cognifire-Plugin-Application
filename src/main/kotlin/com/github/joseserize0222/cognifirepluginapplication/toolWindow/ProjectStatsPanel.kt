package com.github.joseserize0222.cognifirepluginapplication.toolWindow
import com.github.joseserize0222.cognifirepluginapplication.services.FileAnalyzerService
import com.github.joseserize0222.cognifirepluginapplication.utils.FileStatsListener
import com.github.joseserize0222.cognifirepluginapplication.utils.KotlinFileStats
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*


class ProjectStatsPanel(project: Project) : FileStatsListener {
    val content: JComponent
    private var textArea: JTextArea

    init {
        val panel = JPanel(BorderLayout())
        textArea = JTextArea("Select a Kotlin file to show the stats").apply {
            lineWrap = true
            wrapStyleWord = true
            isEditable = false
            margin = JBUI.insets(5) // Optionally add some margin
        }
        panel.add(JScrollPane(textArea), BorderLayout.CENTER)
        content = panel
        project.service<FileAnalyzerService>().addListener(this)
    }

    override fun callback(allStats: KotlinFileStats) {
        val sb = StringBuilder()
        sb.append("Kotlin File Stats for ${allStats.fileName}\n")
        sb.append("All Lines: ${allStats.totalLines}\n")
        sb.append("TODO Lines: ${allStats.todoLines}\n")
        sb.append("Longest Function: ${allStats.getFunctionName()} with ${allStats.getFunctionLines()} ${ if (allStats.getFunctionLines() == 1) "line" else "lines"}\n")
        sb.append("Body Expression:\n")
        sb.append(allStats.getFunctionContent())
        val stats = sb.toString().trimIndent()
        SwingUtilities.invokeLater {
            textArea.text = stats
        }
    }

}