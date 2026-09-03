package com.bbeniful.teleprompter

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun PrompterView(
    state: TeleprompterState,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier) {
        val halfViewport = maxHeight / 2

        Column(Modifier.verticalScroll(state.scroll, enabled = !state.playing)) {
            Spacer(Modifier.height(halfViewport))
            ScriptText(script = state.script, spokenChars = state.spokenChars)
            Spacer(Modifier.height(halfViewport))
        }
    }
}

@Composable
private fun ScriptText(
    script: String,
    spokenChars: Int,
    modifier: Modifier = Modifier,
) {
    val styled = remember(script, spokenChars) { highlight(script, spokenChars) }

    Text(
        text = styled,
        fontSize = PrompterTheme.ScriptFontSize,
        lineHeight = PrompterTheme.ScriptLineHeight,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth().padding(horizontal = 32.dp),
    )
}

private fun highlight(script: String, spokenChars: Int): AnnotatedString =
    buildAnnotatedString {
        val cut = spokenChars.coerceIn(0, script.length)
        withStyle(SpanStyle(color = PrompterTheme.Spoken)) { append(script.take(cut)) }
        withStyle(SpanStyle(color = PrompterTheme.Pending)) { append(script.drop(cut)) }
    }