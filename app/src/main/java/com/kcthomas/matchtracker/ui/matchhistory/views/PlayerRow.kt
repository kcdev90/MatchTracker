package com.kcthomas.core.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcthomas.core.R
import com.kcthomas.core.ui.composable.ripplelessClickable
import com.kcthomas.core.ui.theme.Gray
import com.kcthomas.core.ui.theme.GrayLight
import com.kcthomas.core.ui.theme.Warning

private const val TEXT_FIELD_WIDTH = 150
private const val TEXT_FIELD_HEIGHT = 30
private val roundedCornerShape = RoundedCornerShape(4.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayerRow(
    nameLabel: String,
    name: String,
    isNameError: Boolean,
    onNameClear: () -> Unit,
    onNameChange: (String) -> Unit,
    score: String,
    isScoreError: Boolean,
    onScoreChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Name TextField
            Column {
                Text(
                    text = nameLabel,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(Modifier.height(4.dp))

                Box {
                    BasicTextField(
                        modifier = Modifier
                            .width(TEXT_FIELD_WIDTH.dp)
                            .height(TEXT_FIELD_HEIGHT.dp)
                            .background(
                                color = GrayLight,
                                shape = roundedCornerShape
                            )
                            .then(
                                if (isNameError) {
                                    Modifier.border(
                                        width = 1.dp,
                                        color = Warning,
                                        shape = roundedCornerShape
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .padding(start = 8.dp, end = 8.dp, top = 6.dp),
                        value = name,
                        onValueChange = onNameChange,
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
                        singleLine = true,
                    )

                    // IconButton's UI and hitbox not consistent
                    Icon(
                        modifier = Modifier
                            .padding(start = (TEXT_FIELD_WIDTH - 25).dp, top = 3.dp)
                            .ripplelessClickable { onNameClear() },
                        imageVector = Icons.Default.Clear,
                        contentDescription = "",
                        tint = Gray
                    )
                }
            }

            // Score TextField
            Column {
                Text(
                    text = stringResource(R.string.score),
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(Modifier.height(4.dp))
                BasicTextField(
                    modifier = Modifier
                        .size(TEXT_FIELD_HEIGHT.dp)
                        .background(
                            color = GrayLight,
                            shape = roundedCornerShape
                        )
                        .then(
                            if (isScoreError) {
                                Modifier.border(
                                    width = 1.dp,
                                    color = Warning,
                                    shape = roundedCornerShape
                                )
                            } else {
                                Modifier
                            }
                        )
                        .padding(start = 6.dp, end = 6.dp, top = 6.dp),
                    value = score,
                    onValueChange = onScoreChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    singleLine = true,
                )
            }
        }

        if (isNameError) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.enter_player_name),
                color = Warning,
                fontSize = 14.sp
            )
        }

        if (isScoreError) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.enter_player_score),
                color = Warning,
                fontSize = 14.sp
            )
        }
    }
}
