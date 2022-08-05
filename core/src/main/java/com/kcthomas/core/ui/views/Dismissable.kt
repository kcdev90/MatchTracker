package com.kcthomas.core.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcthomas.core.extension.isDismissed
import com.kcthomas.core.ui.theme.GrayLight
import com.kcthomas.core.ui.theme.Warning
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  Dismissable(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                coroutineScope.launch {
                    // Wait for animation
                    delay(500)
                    onDismiss()
                }
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            dismissState.dismissDirection?.let { direction ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Warning)
                        .padding(horizontal = 16.dp),
                    contentAlignment = when (direction) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = GrayLight
                    )
                }
            }
        },
    ) {
        AnimatedVisibility(
            visible = !dismissState.isDismissed(),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            content()
        }
    }
}
