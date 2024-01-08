package com.ideaapp.presentation.screens.create


import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import  androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ideaapp.domain.model.Note
import com.ideaapp.R
import com.ideaapp.presentation.navigation.components.Screens
import com.ideaapp.presentation.screens.create.components.CustomTextField
import com.ideaapp.presentation.screens.create.components.EditorControls
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val rememberedScrollBehavior = remember { scrollBehavior }

    val keyboardHeight = remember { mutableStateOf(0.dp) }

    val viewModel = hiltViewModel<CreateViewModel>()

    var title by rememberSaveable { mutableStateOf("") }
    val description = rememberRichTextState()

    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize

    var isTextFieldFocused by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, contentDescription = null
                        )
                    }
                },
                actions = {
                    Text(
                        text = stringResource(id = R.string.create),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = modifier
                            .clickable {


                                if (title.isNotEmpty()) {
                                    viewModel.createNote(
                                        Note(
                                            title = title,
                                            content = description.toHtml(),
                                            emoji = "Emoji"
                                        )
                                    ) {
                                        navController.navigate(Screens.Home.rout)
                                    }
                                }
                            }
                            .padding(6.dp)
                    )
                },
                scrollBehavior = rememberedScrollBehavior,
                modifier = modifier
            )


        },
        content = { contentPadding ->
            Box(modifier = modifier.padding(contentPadding)) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = keyboardHeight.value),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    EditorControls(
                        modifier = Modifier.weight(2f),
                        onBoldClick = {
                            description.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        },
                        onItalicClick = {
                            description.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        },
                        onUnderlineClick = {
                            description.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                        },
                        onTitleClick = {
                            description.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                        },
                        onSubtitleClick = {
                            description.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
                        },
                        onStartAlignClick = {
                            description.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                        },
                        onEndAlignClick = {
                            description.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                        },
                        onCenterAlignClick = {
                            description.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                        },
                        onUnorderedListClick = {
                            description.toggleOrderedList()
                        },
                        onOrderClick = {
                            description.toggleUnorderedList()
                        }
                    )

                    CustomTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            isTextFieldFocused = it.isNotEmpty()
                        },
                        labletext = stringResource(
                            id = R.string.title
                        ),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = modifier
                    )
                    RichTextEditor(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f),
                        state = description,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.note),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                    )


                }
            }
        },
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        )
}





