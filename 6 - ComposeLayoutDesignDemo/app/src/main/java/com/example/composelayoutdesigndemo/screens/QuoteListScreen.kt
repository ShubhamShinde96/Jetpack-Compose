package com.example.composelayoutdesigndemo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composelayoutdesigndemo.R
import com.example.composelayoutdesigndemo.models.Quote

@Composable
fun QuoteListScreen(data: Array<Quote>, onClick: () -> Unit) {
    Column {
        Text(
            text = "Quote App",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp, 24.dp),
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily(Font(R.font.open_sans_medium))
        )
        QuoteList(data = data) {
            onClick()
        }
    }
}