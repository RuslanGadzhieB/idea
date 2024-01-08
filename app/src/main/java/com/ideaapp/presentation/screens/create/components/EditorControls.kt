package com.ideaapp.presentation.screens.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onUnorderedListClick: () -> Unit,
    onOrderClick: () -> Unit

) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var isOrderedListSelected by rememberSaveable { mutableStateOf(false) }
    var isUnorderedListSelected by rememberSaveable { mutableStateOf(false) }

    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }
    var expended by remember {
        mutableStateOf(false)
    }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ControlWrapper(
            selected = isUnorderedListSelected,
            onChangeClick = { isUnorderedListSelected = it },
            onClick = onUnorderedListClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatListBulleted,
                contentDescription = "isUnorderedList",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        ControlWrapper(
            selected = isOrderedListSelected,
            onChangeClick = { isOrderedListSelected = it },
            onClick = onOrderClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatListNumbered,
                contentDescription = "isUnorderedList",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

//        DropdownMenu(
//            expanded = expended,
//            onDismissRequest = { expended = false },
//            offset = DpOffset(x = 10.dp, y = 8.dp),
//            modifier = modifier
//                .padding(6.dp)
//                .clip(MaterialTheme.shapes.small)
//
//        ) {
//            DropdownMenuItem(text = {},
//                onClick = { onStartAlignClick() },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.FormatAlignLeft,
//                        contentDescription = "Start Align Control",
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
//                })
//
//            DropdownMenuItem(text = {},
//                onClick = { onCenterAlignClick() },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.FormatAlignCenter,
//                        contentDescription = "Center Align Control",
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
//                })
//
//            DropdownMenuItem(text = {},
//                onClick = { onEndAlignClick() },
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.FormatAlignRight,
//                        contentDescription = "End Align Control",
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
//                })
//        }

        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}