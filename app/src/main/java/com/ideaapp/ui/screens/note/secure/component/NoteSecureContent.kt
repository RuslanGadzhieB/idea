package com.ideaapp.ui.screens.note.secure.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ideaapp.ui.navigation.components.Screens
import com.ideaapp.ui.screens.note.main.components.NoteItem
import com.ideasapp.domain.model.Note


@Composable
fun NoteSecureContent(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    paddingValues: PaddingValues,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = paddingValues,
    ) {
        items(
            notes,
            key = { note -> note.id }) { note ->
            NoteItem(
                title = note.title,
                image = note.imageUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 5.dp)
                    .clickable { navController.navigate(Screens.NoteCreateEdit.rout + "?noteId=${note.id}") }
            )
        }

        repeat(20) {
            item {
                Spacer(modifier = modifier.height(15.dp))
            }
        }

    }
}
