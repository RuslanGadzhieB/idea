package com.ideaapp.ui.components

import android.content.Context
import android.widget.Toast

fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

