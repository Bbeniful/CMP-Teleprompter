package com.bbeniful.teleprompter

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos

@Stable
class TeleprompterState(
    initialScript: String,
    val scroll: ScrollState,
) {
    var script by mutableStateOf(initialScript)
        private set

    var editing by mutableStateOf(true)
        private set

    var playing by mutableStateOf(false)
        private set

    var scrollSpeed by mutableStateOf(DEFAULT_SCROLL_SPEED)

    var charsPerSecond by mutableStateOf(DEFAULT_CHARS_PER_SECOND)

    private var spoken by mutableStateOf(0f)
    private var resetRequested by mutableStateOf(false)

    val spokenChars: Int by derivedStateOf { spoken.toInt() }

    val hasScript: Boolean by derivedStateOf { script.isNotBlank() }

    fun updateScript(value: String) {
        script = value
        reset()
    }

    fun edit() {
        editing = true
        reset()
    }

    fun startPrompting() {
        if (!hasScript) return
        editing = false
        reset()
    }

    fun togglePlayback() {
        if (editing || !hasScript) return
        playing = !playing
    }

    fun reset() {
        playing = false
        resetRequested = true
    }

    suspend fun run() {
        var offset = 0f
        var last = withFrameNanos { it }

        while (true) {
            val now = withFrameNanos { it }
            val elapsed = (now - last) / NANOS_PER_SECOND
            last = now

            if (resetRequested) {
                resetRequested = false
                offset = 0f
                spoken = 0f
                scroll.scrollTo(0)
                continue
            }

            if (!playing) {
                offset = scroll.value.toFloat()
                continue
            }

            spoken = (spoken + charsPerSecond * elapsed).coerceAtMost(script.length.toFloat())

            offset += scrollSpeed * elapsed
            if (offset >= scroll.maxValue) {
                offset = scroll.maxValue.toFloat()
                playing = false
            }
            scroll.scrollTo(offset.toInt())
        }
    }

    companion object {
        const val DEFAULT_SCROLL_SPEED = 10f
        const val DEFAULT_CHARS_PER_SECOND = 10f
        val SCROLL_SPEED_RANGE = 10f..300f
        val CHARS_PER_SECOND_RANGE = 3f..40f
        private const val NANOS_PER_SECOND = 1_000_000_000f
    }
}

@Composable
fun rememberTeleprompterState(initialScript: String = ""): TeleprompterState {
    val scroll = rememberScrollState()
    return remember(scroll) { TeleprompterState(initialScript, scroll) }
}