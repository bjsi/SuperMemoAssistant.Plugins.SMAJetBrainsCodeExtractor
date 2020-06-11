package com.example.SMACodeExtractor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.actionSystem.PlatformDataKeys
import java.lang.management.PlatformManagedObject


class HelloAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val selectedText = getSelectedText(e) ?: return
        val projectName = getProjectName(e) ?: return
        val filePath = getFilePath(e) ?: return
        val language = getLanguage(e) ?: return
        val dialog = ConfirmExtractDialogWrapper(selectedText, projectName, filePath, language)
        if (dialog.showAndGet()){
            val comment = dialog.comment.text
            val priority = dialog.priority.value
            if (priority == null || comment.isNullOrEmpty())
                return
            CreateExtract(selectedText, comment, priority, filePath, projectName, language)
        }
    }

    private fun getLanguage(e: AnActionEvent): String? {
        val vfile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        if (vfile != null)
            return vfile?.fileType?.name
        return null
    }

    private fun getProjectName(e: AnActionEvent): String? {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        if (editor != null)
            return editor?.project?.name
        return null
    }

    private fun getFilePath(e: AnActionEvent) : String? {
        val vfile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        if (vfile != null)
            return vfile?.path
        return null
    }

    private fun getSelectedText(e: AnActionEvent): String? {
        val editor = e.getData(PlatformDataKeys.EDITOR)
        if (editor != null){
            return editor.selectionModel.selectedText
        }
        return null
    }
}