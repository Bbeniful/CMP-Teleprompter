package com.bbeniful.teleprompter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 780.dp, height = 540.dp),
        title = "Teleprompter",
        transparent = true,
        undecorated = true,
    ) {
        TeleprompterApp(onClose = ::exitApplication)
    }
}

@Composable
private fun WindowScope.TeleprompterApp(onClose: () -> Unit) {
    val state = rememberTeleprompterState()
    var seeThrough by remember { mutableStateOf(false) }
    val backdropAlpha by animateFloatAsState(if (seeThrough) 0.3f else 1f)

    LaunchedEffect(state) { state.run() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrompterTheme.Backdrop.copy(alpha = backdropAlpha)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DragHandle()

        if (state.editing) {
            ScriptEditor(
                script = state.script,
                onScriptChange = state::updateScript,
                modifier = Modifier.weight(1f).fillMaxWidth(),
            )
        } else {
            PrompterView(state = state, modifier = Modifier.weight(1f))
        }

        ControlBar(
            state = state,
            seeThrough = seeThrough,
            onToggleSeeThrough = { seeThrough = !seeThrough },
            onClose = onClose,
        )
    }
}

@Composable
private fun WindowScope.DragHandle() {
    WindowDraggableArea {
        Spacer(Modifier.fillMaxWidth().height(28.dp))
    }
}