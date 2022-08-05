package com.kcthomas.matchtracker.ui.leaderboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kcthomas.core.R
import com.kcthomas.core.ui.theme.Gray
import com.kcthomas.core.ui.theme.White
import com.kcthomas.core.ui.views.LabeledRadioButton
import com.kcthomas.core.ui.views.ScreenHeader
import com.kcthomas.core.util.LoadState
import com.kcthomas.matchtracker.ui.leaderboard.views.PlayerCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel = hiltViewModel(),
    openDrawer: () -> Unit
) {
    val viewState = viewModel.viewState.observeAsState().value
    val orderState = viewModel.orderState.observeAsState().value
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column {
                ScreenHeader(
                    title = stringResource(R.string.order_by),
                    trailingButton = {
                        TextButton(onClick = {
                            coroutineScope.launch { bottomSheetState.hide() }
                        }) {
                            Text(
                                text = stringResource(R.string.done),
                                color = White
                            )
                        }
                    }
                )
                OrderBy.values().forEach {
                    LabeledRadioButton(
                        label = stringResource(it.getResId()),
                        selected = it == orderState,
                        onClick = { viewModel.updateOrderState(it) }
                    )
                }
            }
        }
    ) {
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
                title = stringResource(R.string.leaderboard),
                trailingButton = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leaderboard),
                            contentDescription = stringResource(R.string.leaderboard)
                        )
                    }
                }
            )

            when (viewState) {
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
                            text = stringResource(R.string.failure_load_players),
                            fontStyle = FontStyle.Italic,
                            color = Gray
                        )
                    }
                }

                is LoadState.Success -> {
                    val players = viewState.data
                    if (players.isNotEmpty()) {
                        val lazyListState = rememberLazyListState()
                        LazyColumn(
                            state = lazyListState
                        ) {
                            itemsIndexed(
                                items = players,
                                key = { _, players -> players.id }
                            ) { index, player ->
                                PlayerCard(index = index, player = player)
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
    }
}
