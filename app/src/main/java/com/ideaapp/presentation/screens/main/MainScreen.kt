package com.ideaapp.presentation.screens.main


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ideaapp.R
import com.ideaapp.presentation.navigation.components.Screens
import com.ideaapp.presentation.screens.main.components.LargeFAB
import com.ideaapp.presentation.ui.theme.components.NoteItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)


    var expended by remember {
        mutableStateOf(false)
    }


    val viewModel = hiltViewModel<MainViewModel>()

    val notes = viewModel.notes.observeAsState(listOf()).value

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),

                modifier = modifier,
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name), style = TextStyle(
                            fontSize = 29.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onBackground,
                        ), modifier = modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(onClick = { expended = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert, contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = expended,
                        onDismissRequest = { expended = false },
                        offset = DpOffset(x = 10.dp, y = 8.dp),
                        modifier = modifier
                            .padding(6.dp)
                            .clip(MaterialTheme.shapes.small)

                    ) {

                        DropdownMenuItem(text = {
                            Text(
                                text = stringResource(id = R.string.secure_notes),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }, onClick = { }, trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_shield),
                                contentDescription = stringResource(id = R.string.secure_notes)
                            )
                        })

                        Divider(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )


                        DropdownMenuItem(text = {
                            Text(
                                text = stringResource(id = R.string.trash),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }, onClick = { }, trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = stringResource(id = R.string.trash)
                            )
                        })

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )


                        DropdownMenuItem(text = {
                            Text(
                                text = stringResource(id = R.string.settings),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }, onClick = { }, trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = stringResource(id = R.string.settings)
                            )
                        })
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            LargeFAB(onClick = { navController.navigate(Screens.Create.rout) }, listState)
        },
        content = { innerPadding ->

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                state = listState
            ) {
                items(notes) { note ->
                    NoteItem(
                        title = note.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 16.dp)
                            .clickable { navController.navigate(Screens.Details.rout + "/${note.id}") }
                    )
                }
            }
        },
    )
}

