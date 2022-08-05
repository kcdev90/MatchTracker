package com.kcthomas.matchtracker.ui.matchhistory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kcthomas.core.R
import com.kcthomas.core.ui.theme.Gray
import com.kcthomas.core.ui.views.ScreenHeader
import com.kcthomas.core.util.LoadState
import com.kcthomas.domain.match.Match
import com.kcthomas.matchtracker.ui.matchhistory.views.InsertMatchDialog
import com.kcthomas.matchtracker.ui.matchhistory.views.MatchCard

@Composable
fun MatchHistoryScreen(
    viewModel: MatchHistoryViewModel = hiltViewModel(),
    openDrawer: () -> Unit
) {
    var showAddMatchDialog by rememberSaveable { mutableStateOf(false) }
    var currentMatch: Match? by rememberSaveable { mutableStateOf(null) }

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(
            leadingButton = {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            },
            title = stringResource(R.string.match_history),
            trailingButton = {
                IconButton(onClick = { showAddMatchDialog = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = stringResource(R.string.insert_match)
                    )
                }
            }
        )

        when (val viewState = viewModel.viewState.observeAsState().value) {
            LoadState.InFlight -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                }
            }

            LoadState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_warning),
                        contentDescription = stringResource(R.string.warning),
                        tint = Gray
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.failure_load_matches),
                        fontStyle = FontStyle.Italic,
                        color = Gray
                    )
                }
            }

            is LoadState.Success -> {
                val matches = viewState.data
                if (matches.isNotEmpty()) {
                    val lazyListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyListState
                    ) {
                        itemsIndexed(
                            items = matches,
                            key = { _, match -> match.id }
                        ) { index, match ->
                            MatchCard(
                                index = index,
                                match = match,
                                onDismiss = viewModel::deleteMatch,
                                onClick = {
                                    currentMatch = it
                                    showAddMatchDialog = true
                                }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.empty_message),
                            fontStyle = FontStyle.Italic,
                            color = Gray
                        )
                    }
                }
            }
        }
    }

    InsertMatchDialog(
        showDialog = showAddMatchDialog,
        match = currentMatch,
        closeDialog = {
            showAddMatchDialog = false
            currentMatch = null
        },
        onInsertMatch = viewModel::insertMatch
    )
}
