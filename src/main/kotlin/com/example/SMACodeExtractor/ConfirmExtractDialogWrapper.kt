package com.example.SMACodeExtractor
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class ConfirmExtractDialogWrapper(selectedText: String, projectName: String, filePath: String, language: String) : DialogWrapper(true) {

    val panel = JPanel(GridBagLayout())
    val code = JEditorPane()
    val project = JTextField()
    val file = JTextField()
    val lang = JTextField()
    val comment = JTextArea()
    val priority = JSlider()

    init {
        init()
        title = "Confirm Code Extraction"
        code.text = selectedText
        project.text = projectName
        file.text = filePath
        lang.text = language
        comment.rows = 5
        ConfigurePrioritySlider()
    }

    private fun ConfigurePrioritySlider(){
        priority.majorTickSpacing = 10
        priority.minorTickSpacing = 1
        priority.maximum = 100
        priority.paintTicks = true
        priority.paintLabels = true
    }

    override fun createCenterPanel(): JComponent? {
        var gb = GridBag()
            .setDefaultInsets(Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
            .setDefaultWeightX(1.0)
            .setDefaultFill(GridBagConstraints.HORIZONTAL)

        panel.preferredSize = Dimension(400, 300)

        // Code
        panel.add(label("Code Extract: "), gb.nextLine().next().weightx(0.2))
        panel.add(code, gb.nextLine().next().weightx(0.8))

        // File
        panel.add(label("File: "), gb.nextLine().next().weightx(0.2))
        panel.add(file, gb.nextLine().next().weightx(0.8))

        // Project
        panel.add(label("Project: "), gb.nextLine().next().weightx(0.2))
        panel.add(project, gb.nextLine().next().weightx(0.8))

        // Language
        panel.add(label("Language: "), gb.nextLine().next().weightx(0.2))
        panel.add(lang, gb.nextLine().next().weightx(0.8))

        // Comment
        panel.add(label("Comment: "), gb.nextLine().next().weightx(0.2))
        panel.add(comment, gb.nextLine().next().weightx(0.8))

        // Priority
        panel.add(label("Priority (%): "), gb.nextLine().next().weightx(0.2))
        panel.add(priority, gb.nextLine().next().weightx(0.8))

        return panel
    }

    private fun label(text: String): JComponent {
        val label = JBLabel(text)
        label.componentStyle = UIUtil.ComponentStyle.SMALL
        label.fontColor = UIUtil.FontColor.BRIGHTER
        label.border = JBUI.Borders.empty(0, 5, 2, 0)
        return label
    }

}