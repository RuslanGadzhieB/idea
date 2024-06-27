package com.ideaapp.shared.compose.screens.task.create_edit


//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.ime
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import com.ideaapp.shared.compose.components.TopBar
//import com.ideaapp.shared.compose.ui.screens.task.create_edit.component.DetailBottomBar
//import com.ideaapp.shared.compose.ui.screens.task.create_edit.component.TaskDetailComponent
//import kotlinx.coroutines.flow.collectLatest
//import org.koin.androidx.compose.koinViewModel
//
//
//@Composable
//fun TaskDetailScreen(
//    modifier: Modifier = Modifier,
//    viewModel: TaskDetailViewModel = koinViewModel(),
//    navController: NavHostController,
//) {
//    val taskState by viewModel.taskState.collectAsState()
//
//    var complete by remember(taskState.isComplete) {
//        mutableStateOf(taskState.isComplete)
//    }
//    val snackBarHostState = remember { SnackbarHostState() }
//    LaunchedEffect(key1 = true) {
//        viewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                is TaskDetailViewModel.UiEvent.Save -> {
//                    navController.navigateUp()
//                }
//
//                is TaskDetailViewModel.UiEvent.ShowSnackBar -> {
//                    snackBarHostState.showSnackbar(event.message)
//                }
//
//                TaskDetailViewModel.UiEvent.Delete -> {
//                    navController.navigateUp()
//                }
//            }
//        }
//    }
//    Scaffold(
//        modifier = modifier,
//        contentWindowInsets = WindowInsets.ime,
//        snackbarHost = {
//            SnackbarHost(hostState = snackBarHostState)
//        },
//        topBar = {
//            TopBar(save = {
//                if (navController.canNavigate()) {
//                    viewModel.onEvent(TaskDetailUiEvent.Save)
//                }
//            }, delete = {
//                if (navController.canNavigate()) {
//                    viewModel.onEvent(TaskDetailUiEvent.Delete)
//                }
//            },
//                action = {
//
//                }
//            )
//        },
//        bottomBar = {
//            if (viewModel.currentId != 0L) {
//                DetailBottomBar(complete = complete) {
//                    viewModel.onEvent(TaskDetailUiEvent.UpdateComplete(!complete))
//                    complete = !complete
//                }
//            }
//        },
//        content = {
//            TaskDetailComponent(
//                viewModel = viewModel,
//                taskState = taskState,
//                innerPadding = it,
//                modifier = modifier
//            )
//        }
//    )
//}