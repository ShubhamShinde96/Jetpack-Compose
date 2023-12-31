package com.example.composelayoutdesigndemo.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.composelayoutdesigndemo.models.Quote

@Composable
fun QuoteList(data: Array<Quote>, onClick: () -> Unit) {
    
    LazyColumn(content = {
        items(data) {
            QuoteListItem(it) {
                onClick()
            }
        }
    })
}