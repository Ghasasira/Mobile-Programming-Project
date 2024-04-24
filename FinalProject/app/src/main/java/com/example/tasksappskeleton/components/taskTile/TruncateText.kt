package com.example.tasksappskeleton.components.taskTile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun TruncateText(text: String, maxWords: Int = 33) {
    val words = text.split(" ")
    val truncatedText = if (words.size > maxWords) {
        words.take(maxWords).joinToString(" ") + "..."
    } else {
        text
    }
    Text(text = truncatedText, fontSize = 14.sp)
}

@Composable
fun TruncateFileTitle(text: String, maxLetters: Int = 8) {
    val words = text.split("")
    val truncatedText = if (words.size > maxLetters) {
        words.take(maxLetters).joinToString("") + "..."
    } else {
        text
    }
    Text(text = truncatedText, fontSize = 18.sp)
}
