package utils

import javax.swing.filechooser.FileSystemView

val desktopPath: String by lazy {
    val fileSystemView = FileSystemView.getFileSystemView()
    return@lazy fileSystemView.homeDirectory.path
}