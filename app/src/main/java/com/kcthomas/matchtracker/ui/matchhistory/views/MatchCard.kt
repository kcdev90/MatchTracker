package com.kcthomas.matchtracker.ui.matchhistory.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kcthomas.core.ui.theme.*
import com.kcthomas.core.ui.views.Dismissable
import com.kcthomas.domain.match.Match

@Composable
fun MatchCard(
    index: Int,
    match: Match,
    onDismiss: (Match) -> Unit,
    onClick: (Match) -> Unit
) {
    Dismissable(onDismiss = { onDismiss(match) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(match) },
            backgroundColor = if (index % 2 == 0) White else GrayLight
        ) {
            Column(
                modifier = Modifier.padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = match.player1Name,
                        style = playerNameStyle
                    )
                    Score(
                        modifier = Modifier.weight(1f),
                        player1Score = "${match.player1Score}",
                        player2Score = "${match.player2Score}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = match.player2Name,
                        style = playerNameStyle
                    )
                }

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 2.dp)
                )

                Text(
                    text = match.timestamp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
private fun Score(
    modifier: Modifier = Modifier,
    player1Score: String,
    player2Score: String
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = when {
                    player1Score > player2Score -> {
                        Brush.linearGradient(colors = listOf(Green, GreenLight))
                    }
                    player1Score < player2Score -> {
                        Brush.linearGradient(colors = listOf(Pink, PinkLight))
                    }
                    else -> {
                        Brush.linearGradient(colors = listOf(Yellow, YellowLight))
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player1Score,
            style = playerScoreStyle
        )
    }
    val height = 3.0.dp
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(16.dp)
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(Color.Black)

    )
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = when {
                    player1Score > player2Score -> {
                        Brush.linearGradient(colors = listOf(Pink, PinkLight))
                    }
                    player1Score < player2Score -> {
                        Brush.linearGradient(colors = listOf(Green, GreenLight))
                    }
                    else -> {
                        Brush.linearGradient(colors = listOf(Yellow, YellowLight))
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player2Score,
            style = playerScoreStyle
        )
    }
}

@Preview
@Composable
fun PreviewMatchCard() {
    MatchTrackerTheme {
        Column {
            MatchCard(
                index = 0,
                match = Match(
                    id = "",
                    player1Name = "Amos",
                    player1Score = 4,
                    player2Name = "Diego",
                    player2Score = 5,
                    timestamp = "Jul 26, 2022 1:45 PM"
                ),
                onDismiss = {},
                onClick = {}
            )
            MatchCard(
                index = 1,
                match = Match(
                    id = "",
                    player1Name = "Joel",
                    player1Score = 5,
                    player2Name = "Tim",
                    player2Score = 2,
                    timestamp = "Jul 25, 2022 9:30 AM"
                ),
                onDismiss = {},
                onClick = {}
            )
        }
    }
}
