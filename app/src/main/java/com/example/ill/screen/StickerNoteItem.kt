package com.example.ill.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ill.room.Note
import com.example.ill.ui.theme.getPriorityColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.MaterialTheme

@Composable
fun StickerNoteItem(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(getPriorityColor(note.priority)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(note.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
            Text(note.content, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(bottom = 4.dp))
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Priority: ${note.priority}", style = MaterialTheme.typography.labelSmall)
                Text("Date: ${note.date}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
