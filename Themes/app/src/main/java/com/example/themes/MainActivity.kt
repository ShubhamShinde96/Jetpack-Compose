package com.example.themes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.themes.ui.theme.ThemesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {

//    LocalConfiguration.current.uiMode  // Got this from ui.theme/Theme.kt isSystemInDarkTheme() > _isSystemInDarkTheme() >
//    LocalConfiguration.current. // chec the properties by putting . and we can use them where we are using compose in
      // our application.
      // Similarly if we want to access context in compose functions then we can use "LocalContext"
//    LocalContext.current
//    LocalContext.current.applicationContext
      // Explore more properties of LocalContext.current

    var theme = remember {
        mutableStateOf(false)
    }
    ThemesTheme(theme.value) { // Theme name created as per our app name, our app name is theme
        // A surface container using the 'background' color from the theme.
        // We have to check Theme.kt
        // Check each file in ui.theme package for more details

        Column(
            modifier = Modifier.background(MaterialTheme
                .colorScheme.background)
        ) {
            Text(
                text = "Cheezy Code",
                style = MaterialTheme.typography.titleLarge // This we have re defined in ui.theme/Type.kt
            )
            Button(onClick = {
                theme.value = !theme.value // this will assign opposite value always, this will help us change the theme.
            }) {
                Text(text = "Change Theme")
            }
            // So here we're changin theme based on this button click.
        }

    }
}
