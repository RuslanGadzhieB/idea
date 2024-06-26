package com.ideaapp.ui.navigation


import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ideaapp.ui.components.FAB
import com.ideaapp.ui.navigation.components.NavBar
import com.ideaapp.ui.navigation.components.Screens
import com.ideaapp.ui.screens.note.create_edit.NoteCreateEditScreen
import com.ideaapp.ui.screens.note.main.NoteScreen
import com.ideaapp.ui.screens.note.secure.NoteSecureScreen
import com.ideaapp.ui.screens.settings.SettingsScreen
import com.ideaapp.ui.screens.task.create_edit.TaskDetailScreen
import com.ideaapp.ui.screens.task.main.TaskScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavHost(
    context: Context,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val floatingBottomRoutes = setOf(Screens.Note.rout, Screens.Task.rout)
    val showFloatingBottom = currentRoute in floatingBottomRoutes

    Scaffold(
        bottomBar = {
            NavBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatingBottom,
            ) {
                FAB(
                    onClick = {
                        if (currentRoute == Screens.Note.rout) navController.navigate(
                            Screens.NoteCreateEdit.rout
                        ) else navController.navigate(
                            Screens.TaskCreateEdit.rout
                        )
                    },
                )
            }
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.Note.rout,
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
                composable(route = Screens.Note.rout) {
                    NoteScreen(
                        navController = navController,
                        hiltViewModel(),
                        context as AppCompatActivity,
                    )
                }

                composable(
                    route = Screens.Secure.rout
                ) {
                    NoteSecureScreen(navController = navController, hiltViewModel())
                }

                composable(
                    route = Screens.NoteCreateEdit.rout + "?noteId={noteId}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.LongType
                            defaultValue = -1L
                        }
                    )
                ) {
                    NoteCreateEditScreen(
                        navController = navController,
                        viewModel = hiltViewModel(),
                        context = context
                    )
                }

                composable(
                    route = Screens.Settings.rout
                ) {
                    SettingsScreen(
                        viewModel = hiltViewModel(),
                        context = context
                    )
                }


                composable(
                    route = Screens.Task.rout,
                ) {
                    TaskScreen(viewModel = hiltViewModel(), navController = navController)
                }

                composable(
                    route = Screens.TaskCreateEdit.rout + "?taskId={taskId}",
                    arguments = listOf(
                        navArgument(
                            name = "taskId"
                        ) {
                            type = NavType.LongType
                            defaultValue = -1L
                        }
                    )
                ) {
                    TaskDetailScreen(viewModel = hiltViewModel(), navController = navController)
                }

            }
        }
    )
}

