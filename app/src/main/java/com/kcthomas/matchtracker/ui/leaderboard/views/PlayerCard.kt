package com.kcthomas.matchtracker.ui.leaderboard.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcthomas.core.R
import com.kcthomas.core.extension.format
import com.kcthomas.core.ui.theme.Bronze
import com.kcthomas.core.ui.theme.MatchTrackerTheme
import com.kcthomas.core.ui.theme.Gold
import com.kcthomas.core.ui.theme.Gray
import com.kcthomas.core.ui.theme.Silver
import com.kcthomas.core.ui.theme.White
import com.kcthomas.domain.player.Player

@Composable
fun PlayerCard(
    index: Int,
    player: Player
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (index < 3) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(R.drawable.ic_trophy),
                        contentDescription = null,
                        tint = when (index) {
                            0 -> Gold
                            1 -> Silver
                            2 -> Bronze
                            else -> White
                        }
                    )
                }
                Text(
                    modifier = Modifier.padding(
                        end = if (index < 3) 0.dp else 4.dp,
                        bottom = if (index < 3) 14.dp else 0.dp
                    ),
                    text = "${index + 1}",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.backslash_regular)),
                )
            }

            Spacer(Modifier.width(24.dp))

            Text(
                text = player.name,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.backslash_regular)),
            )

            Spacer(Modifier.weight(1f))

            Statistic(
                label = "T",
                statistic = player.matchesPlayed
            )

            Slash()

            Statistic(
                label = "W",
                statistic = player.matchesWon
            )

            Slash()

            Statistic(
                label = "R",
                statistic = "${(100 * player.matchesWon.toDouble() / player.matchesPlayed.toDouble()).format()}%"
            )
        }
    }
}

@Composable
private fun Statistic(
    label: String,
    statistic: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            color = Gray,
        )
        Text(
            text = statistic,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun Slash() {
    Text(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 14.dp),
        text = "/",
        fontSize = 10.sp
    )
}

@Preview
@Composable
fun PreviewPlayerCard() {
    MatchTrackerTheme {
        Column {
            Player(
                id = "1",
                name = "Alice",
                matchesPlayed = "10",
                matchesWon = "5"
            )
            Player(
                id = "2",
                name = "Bob",
                matchesPlayed = "9",
                matchesWon = "6"
            )
        }
    }
}
