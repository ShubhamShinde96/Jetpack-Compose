package com.example.rememberupdatedstate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberupdatedstate.ui.theme.RememberUpdatedstateTheme
import kotlinx.coroutines.delay

class Example : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberUpdatedstateTheme {
                App2()
            }
        }
    }
}

fun a() { Log.d("REMEMBER_UPDATED_STATE","I am A from app") }

fun b() { Log.d("REMEMBER_UPDATED_STATE","I am B from app") }

@Composable
fun App2() {
    val state = remember {
        mutableStateOf(::a)
    }
    Button(onClick = { state.value = ::b }) {
        Text(text = "Click to change state")
    }
    LandingScreen(state.value)

}

@Composable
fun LandingScreen(onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(key1 = true) {
        delay(5000)
        currentOnTimeout()
    }
}