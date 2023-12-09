package com.ideaapp.presentation.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteItem(title: String, backgroundColor: Color, modifier: Modifier) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color =  Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 22.dp)
                    .padding(horizontal = 24.dp)
            )

        }
    }


}