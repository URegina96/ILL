package com.example.ill.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ill.AddNoteDialog
import com.example.ill.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun AddNoteDialogPreview() {
    AppTheme {
        AddNoteDialog({}, {})
    }
}
