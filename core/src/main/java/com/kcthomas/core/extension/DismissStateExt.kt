package com.kcthomas.core.extension

import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi

@OptIn(ExperimentalMaterialApi::class)
fun DismissState.isDismissed() =
    isDismissed(DismissDirection.StartToEnd) || isDismissed(DismissDirection.EndToStart)
