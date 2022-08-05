package com.kcthomas.matchtracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kcthomas.core.R
import com.kcthomas.core.ui.theme.Gray
import com.kcthomas.matchtracker.MainActivity.Companion.ROUTE_LEADERBOARD
import com.kcthomas.matchtracker.MainActivity.Companion.ROUTE_MATCH_HISTORY

private val screens = listOf(
    "Match History",
    "Leaderboard"
)

@Composable
fun NavDrawer(
    onDestinationClicked: (String) -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            SampleProfile()
            Divider()
            MatchHistoryRow(onDestinationClicked)
            LeaderboardRow(onDestinationClicked)
        }
    }
}

@Composable
private fun MatchHistoryRow(
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(ROUTE_MATCH_HISTORY) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_history),
            contentDescription = stringResource(R.string.match_history),
            tint = Gray
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = stringResource(R.string.match_history),
            fontSize = 20.sp,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun LeaderboardRow(
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(ROUTE_LEADERBOARD) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_leaderboard),
            contentDescription = stringResource(R.string.leaderboard),
            tint = Gray
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = stringResource(R.string.leaderboard),
            fontSize = 20.sp,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun SampleProfile() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // I'm really not the stalking type, but really wanted a sample image
        // Don't worry, I will remove this image when code review is done"
        Image(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                ),
            painter = painterResource(R.drawable.ic_sample),
            contentDescription = "Don't worry, I will remove this image when code review is done",
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Amanda Githubson",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "agithubson@android4life.com",
            fontSize = 14.sp,
            color = Gray
        )
    }
}
