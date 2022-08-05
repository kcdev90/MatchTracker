package com.kcthomas.matchtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kcthomas.core.ui.theme.MatchTrackerTheme
import com.kcthomas.matchtracker.ui.NavDrawer
import com.kcthomas.matchtracker.ui.leaderboard.LeaderboardScreen
import com.kcthomas.matchtracker.ui.matchhistory.MatchHistoryScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val ROUTE_MATCH_HISTORY = "ROUTE_MATCH_HISTORY"
        const val ROUTE_LEADERBOARD = "ROUTE_LEADERBOARD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchTrackerTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()

                // Keep track of route to prevent navigating to a screen we're already on
                var currentRoute by rememberSaveable { mutableStateOf(ROUTE_MATCH_HISTORY) }

                ModalDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        NavDrawer { destRoute ->
                            if (destRoute != currentRoute) {
                                currentRoute = destRoute
                                navController.navigate(destRoute)
                            }
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_MATCH_HISTORY
                    ) {
                        val openDrawer: () -> Unit = {
                            coroutineScope.launch { drawerState.open() }
                        }

                        composable(route = ROUTE_MATCH_HISTORY) {
                            MatchHistoryScreen(openDrawer = openDrawer)
                        }

                        composable(route = ROUTE_LEADERBOARD) {
                            LeaderboardScreen(openDrawer = openDrawer)
                        }
                    }
                }
            }
        }
    }
}
