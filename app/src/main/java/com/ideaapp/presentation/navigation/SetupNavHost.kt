package com.ideaapp.presentation.navigation


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ideaapp.presentation.screens.create.CreateScreen
import com.ideaapp.presentation.screens.details.DetailsScreen
import com.ideaapp.presentation.screens.main.MainScreen
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ideaapp.presentation.navigation.components.Screens
import com.ideaapp.presentation.navigation.components.items
import com.ideaapp.presentation.screens.task.TaskScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavHost(
    navController: NavHostController,
) {

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Screens.Details.rout + "/{id}" -> false // on this screen bottom bar should be hidden
        Screens.Create.rout -> false // here too
        else -> true // in all other cases show bottom bar
    }
    val listState = rememberLazyGridState()
    val fabVisibility by derivedStateOf {
        listState.firstVisibleItemIndex == 0
    }

    Scaffold(
        bottomBar = {
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            val density = LocalDensity.current
            if (showBottomBar)
                AnimatedVisibility(
                    visible = fabVisibility,
                    enter = slideInVertically {
                        with(density) { 40.dp.roundToPx() }
                    } + fadeIn(),
                    exit = fadeOut(
                        animationSpec = keyframes {
                            this.durationMillis = 120
                        }
                    )
                ) {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            val isSelected = selectedItemIndex == index && navBackStackEntry?.destination?.route == item.route
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) {
                                            item.selectedIcon
                                        } else {
                                            item.unselectedIcon
                                        },
                                        contentDescription = item.route
                                    )
                                }
                            )

                        }

                    }
                }
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.Home.rout,
                enterTransition = {
                    fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                            scaleIn(
                                initialScale = 0.92f,
                                animationSpec = tween(220, delayMillis = 90)
                            )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(90))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                            scaleIn(
                                initialScale = 0.92f,
                                animationSpec = tween(220, delayMillis = 90)
                            )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(90))
                },
            ) {
                composable(route = Screens.Home.rout) {
                    MainScreen(navController = navController, listState)

                }
                composable(
                    route = Screens.Details.rout + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    DetailsScreen(navController = navController, it.arguments?.getString("id"))
                }
                composable(
                    route = Screens.Create.rout
                ) {
                    val context = LocalContext.current

                    CreateScreen(navController = navController, context)

                }

                composable(
                    route = Screens.Task.rout,
                ) {
                    TaskScreen()
                }



            }
        }
    )

}


