package com.bbeniful.teleprompter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ControlBar(
    state: TeleprompterState,
    seeThrough: Boolean,
    onToggleSeeThrough: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (state.editing) {
            Button(onClick = state::startPrompting, enabled = state.hasScript) {
                Text("Kész")
            }
        } else {
            Button(onClick = state::edit) { Text("Edit") }
            Button(onClick = state::togglePlayback) {
                Text(if (state.playing) "Stop" else "Start")
            }
            Button(onClick = state::reset) { Text("Reset") }

            SpeedSlider(
                label = "Vertical scroll speed",
                value = state.scrollSpeed,
                range = TeleprompterState.SCROLL_SPEED_RANGE,
                onValueChange = { state.scrollSpeed = it },
            )

            SpeedSlider(
                label = "Text highlight",
                value = state.charsPerSecond,
                range = TeleprompterState.CHARS_PER_SECOND_RANGE,
                onValueChange = { state.charsPerSecond = it },
            )
        }

        Button(onClick = onToggleSeeThrough) {
            Text(if (seeThrough) "Back" else "Transparent")
        }
        Button(onClick = onClose) { Text("X") }
    }
}

@Composable
private fun SpeedSlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
) {
    Text(label, color = PrompterTheme.Label, fontSize = PrompterTheme.LabelFontSize)
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = range,
        modifier = Modifier.width(120.dp),
    )
}