package com.example.ill.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.ill.AddNoteDialog
import com.example.ill.FilterBar
import com.example.ill.FilterOption
import com.example.ill.R
import com.example.ill.button.AddNoteButton
import com.example.ill.button.DeleteAllNotesButton
import com.example.ill.room.NoteDatabase
import com.example.ill.room.NoteRepository
import com.example.ill.viewModel.getViewModel
import com.example.ill.viewModel.NoteViewModel
import com.example.ill.viewModel.NoteViewModelFactory
import com.example.ill.ui.theme.PastelBlue
import com.example.ill.ui.theme.PastelGray

@Composable
fun NoteApp() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val savedUsername = sharedPreferences.getString("username", "")
    val savedPassword = sharedPreferences.getString("password", "")
    var isLoggedIn by remember { mutableStateOf(savedUsername != "" && savedPassword != "") }
    var showDialog by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        NoteScreen(showDialog, onLogout = {
            sharedPreferences.edit().clear().apply()
            isLoggedIn = false
        }, onShowDialogChange = { showDialog = it })
    } else {
        LoginScreen(onLogin = { username, password, rememberMe ->
            sharedPreferences.edit().apply {
                if (rememberMe) {
                    putString("username", username)
                    putString("password", password)
                }
                apply()
            }
            isLoggedIn = true
        })
    }
}

