package com.example.firstcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstcompose.ui.theme.FirstComposeTheme

class MainActivity : ComponentActivity() {

    // 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Preview(showBackground = true, name = "SayCheezy", showSystemUi = true)
@Composable
fun SayCheezy(name: String = "Shubham") {
    Text(text = name)
}

@Preview(showBackground = true, name = "SayCheezy 2", widthDp = 200, heightDp = 200)
@Composable
fun SayCheezy2(name: String = "Shubham") {
    Text(text = name)
}

//@Preview(showBackground = true, name = "SayCheezy 2", widthDp = 200, heightDp = 200)
@Composable
fun SayCheezy3(name: String) { // if you remove default value then it will show an error
    // for preview, the error will be as non default parameters are not supported for preview.
    // To get rid of this error scenario we remove preview annotation from current fun and
    // create a new function for preview, like below preview function.
    Text(text = name)
}

@Preview(showBackground = true, name = "SayCheezy 2", widthDp = 200, heightDp = 200)
@Composable
private fun PreviewFunction() {
    SayCheezy(name = "CheezyCode")
    // So generally what we do is we create a preview function like this just to preview
    // other composable functions
}

// Open project ViewsWithCompose to see how we can integration compose in legacy project