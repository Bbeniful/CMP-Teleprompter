package com.bbeniful.teleprompter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ScriptEditor(
    script: String,
    onScriptChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.padding(horizontal = 32.dp, vertical = 8.dp)) {
        if (script.isEmpty()) {
            Text(
                text = "Paste the script here...",
                color = PrompterTheme.Pending,
                fontSize = PrompterTheme.EditorFontSize,
            )
        }

        BasicTextField(
            value = script,
            onValueChange = onScriptChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = TextStyle(
                color = PrompterTheme.Label,
                fontSize = PrompterTheme.EditorFontSize,
                lineHeight = PrompterTheme.EditorLineHeight,
            ),
            cursorBrush = SolidColor(PrompterTheme.Spoken),
        )
    }
}