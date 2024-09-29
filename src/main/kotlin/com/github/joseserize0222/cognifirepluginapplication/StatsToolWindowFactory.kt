package com.github.joseserize0222.cognifirepluginapplication

import com.github.joseserize0222.cognifirepluginapplication.ProjectStatsPanel
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory


class StatsToolWindowFactory: ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val statsPanel = ProjectStatsPanel(project)
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(statsPanel.content, "", false)
        toolWindow.contentManager.addContent(content)
    }
}