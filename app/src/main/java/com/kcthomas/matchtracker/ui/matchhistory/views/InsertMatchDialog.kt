package com.kcthomas.matchtracker.ui.matchhistory.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcthomas.core.R
import com.kcthomas.core.ui.views.PlayerRow
import com.kcthomas.core.util.currentDateTimeString
import com.kcthomas.core.util.validateName
import com.kcthomas.core.util.validateScore
import com.kcthomas.domain.match.Match
import java.util.UUID

@Composable
fun InsertMatchDialog(
    showDialog: Boolean,
    match: Match? = null,
    closeDialog: () -> Unit,
    onInsertMatch: (Match) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.width(250.dp),
            onDismissRequest = closeDialog,
            buttons = {
                var player1Name by rememberSaveable { mutableStateOf(match?.player1Name ?: "") }
                var player1NameError by rememberSaveable { mutableStateOf(false) }
                var player1Score by rememberSaveable { mutableStateOf(match?.player1Score ?: "") }
                var player1ScoreError by rememberSaveable { mutableStateOf(false) }
                var player2Name by rememberSaveable { mutableStateOf(match?.player2Name ?: "") }
                var player2NameError by rememberSaveable { mutableStateOf(false) }
                var player2Score by rememberSaveable { mutableStateOf(match?.player2Score ?: "") }
                var player2ScoreError by rememberSaveable { mutableStateOf(false) }

                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.insert_match_dialog_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(4.dp))

                    PlayerRow(
                        nameLabel = stringResource(R.string.player1_name),
                        name = player1Name,
                        isNameError = player1NameError,
                        onNameClear = { player1Name = "" },
                        onNameChange = { name ->
                            player1Name = name.filter { it.isLetterOrDigit() }
                            player1NameError = false
                        },
                        score = player1Score,
                        isScoreError = player1ScoreError,
                        onScoreChange = { score ->
                            player1Score = score.filter { it.isDigit() }
                            player1ScoreError = false
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    PlayerRow(
                        nameLabel = stringResource(R.string.player2_name),
                        name = player2Name,
                        isNameError = player2NameError,
                        onNameClear = { player2Name = "" },
                        onNameChange = { name ->
                            player2Name = name.filter { it.isLetterOrDigit() }
                            player2NameError = false
                        },
                        score = player2Score,
                        isScoreError = player2ScoreError,
                        onScoreChange = { score ->
                            player2Score = score.filter { it.isDigit() }
                            player2ScoreError = false
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = closeDialog) {
                            Text(
                                text = stringResource(android.R.string.cancel),
                                color = MaterialTheme.colors.secondary
                            )
                        }

                        TextButton(onClick = {
                            player1NameError = player1Name.validateName()
                            player1ScoreError = player1Score.validateScore()
                            player2NameError = player2Name.validateName()
                            player2ScoreError = player2Score.validateScore()

                            if (
                                !player1NameError &&
                                !player1ScoreError &&
                                !player2NameError &&
                                !player2ScoreError
                            ) {
                                onInsertMatch(
                                    // If the user clicked on a Match card, use existing ID
                                    // Otherwise, assign a new random one
                                    Match(
                                        id = match?.id ?: UUID.randomUUID().toString(),
                                        player1Name = player1Name,
                                        player1Score = player1Score,
                                        player2Name = player2Name,
                                        player2Score = player2Score,
                                        timestamp = match?.timestamp ?: currentDateTimeString()
                                    )
                                )
                                closeDialog()
                            }
                        }) {
                            Text(
                                text = stringResource(android.R.string.ok),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
        )
    }
}
