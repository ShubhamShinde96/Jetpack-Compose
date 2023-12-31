package com.example.legacyviewproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.legacyviewproject.ui.theme.LegacyViewProjectTheme

class LayoutsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextInputField()
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
private fun PreviewFunction() {
//    Text(text = "A", fontSize = 24.sp)
//    Text(text = "B", fontSize = 24.sp) // As these are overlaping each other,
    // we need to move them inside column
    Column(
        // vertical arrangement is to tell how rest of the space to be utilised
        // Like in this case below B there is lot of space remaining till bottom
//        verticalArrangement = Arrangement.SpaceBetween
        verticalArrangement = Arrangement.SpaceEvenly, // there are many such differnt
    // options, checkout all of them.
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "A", fontSize = 24.sp)
        Text(text = "B", fontSize = 24.sp)
    } // Now Text B is being displayed below Text A
    // This is same like vertical linear layout

    // Same way Row. Used like horizontal linear layout
    // notice here arrangement is horizontal and alignment is vertical
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "C", fontSize = 24.sp)
        Text(text = "D", fontSize = 24.sp)
    }

    // Box is like FrameLayout, where we used to add components on top of each other.
    Box(contentAlignment = Alignment.BottomStart) {
       Image(
           painter = painterResource(id = R.drawable.baseline_contactless_24),
           contentDescription = "Dummy"
       )
       Image(
           painter = painterResource(id = R.drawable.ic_android_black_24dp),
           contentDescription = "Dummy"
       )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField() {
    val state = remember { mutableStateOf("") }
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
            Log.d("SHUBZ_LOGS", it)
        },
        label = { Text(text = "Enter message") }
    )
}

