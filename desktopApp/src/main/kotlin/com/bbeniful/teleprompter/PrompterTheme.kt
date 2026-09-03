package com.bbeniful.teleprompter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

object PrompterTheme {
    val Backdrop = Color(0xFF16161A)
    val Spoken = Color(0xFFFFC947)
    val Pending = Color.White.copy(alpha = 0.45f)
    val Label = Color.White

    val ScriptFontSize = 34.sp
    val ScriptLineHeight = 46.sp
    val EditorFontSize = 15.sp
    val EditorLineHeight = 22.sp
    val LabelFontSize = 12.sp
}