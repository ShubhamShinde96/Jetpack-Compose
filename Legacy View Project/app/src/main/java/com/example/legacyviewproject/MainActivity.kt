package com.example.legacyviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Here we'll see how we can integrate compose in existing legacy xml view based project

        // To do this open xml layout file and add ComposeView element
        // We need to add compose dependencies and make other changes in build.gradle file to support compose

        // now that we have configured compose and we have made changes in xml file too
        val composeLayout = findViewById<ComposeView>(R.id.compose_layout)
        composeLayout.setContent {
            SayCheezy(name = "CheezyCode")
//            ImageComposable()
            ButtonComposable()
        }

    }
}

@Preview
@Composable
fun SayCheezy(name: String) {
    Text(
        text = name,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        color = Color.Red,
        fontSize = 36.sp,
        textAlign = TextAlign.Center
    )
}

//@Preview
@Composable
fun ImageComposable() {
    Image(
        painter = painterResource(id = R.drawable.blue_design),
        contentDescription = "Dummy Image",
//        colorFilter = ColorFilter.tint(Color.Magenta),
        contentScale = ContentScale.Crop
            // first 2 params are compulsory for image
    
    )
}

@Preview
@Composable
fun ButtonComposable() {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, // This is for text color
                containerColor = Color.LightGray // This is for background color
            ),
            enabled = true,
        shape = CircleShape,
            modifier = Modifier.size(width = 130.dp, height = 40.dp)
//            modifier = Modifier
//                .height(100.dp)
//                .width(40.dp)
        ) {
            Text(text = "Click here")
            Image(
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = "Dummy Img"
            )
            // This is how we can render both text and image inside a button
            // This was not possible with the xml as it is based on inheritance
            // but compose declarative pattern is based on composition hence we can do it here.
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
fun TextFieldComposable() {
    TextField(
        value = "Hello Shubham",
        onValueChange = {},
        label = { Text(text = "Enter Message")},
        placeholder = {} // So the placeholder here expect a composable, so here we can define a custom placeholder as we have to provide a composable
        // There are some more properties which expect composable, open TextField to check which properties expecting composable.
    )
}

// Remember: Whatever UI we create in compose, it gets created based on data
// You give data as a input and it generates the UI based on that data.
// If any change happens in the provided data then compose will update the UI based on latest updates in data.
// There are 2 types of compositions
// 1] Initial Composition: This is when first time UI created on first data
// 2] Recomposition: This is when UI gets updated based on data updates
// So basically it's state which plays role for updating compose UI.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput() {
    // val state = mutableStateOf("") // There are many cases where compose will recall
    // this method, and if that happens this state variable gets recreated and our state
    // will be lost. To prevent this we need to use "remember" keyword before "mutableStateOf"
    // "remember" will keep compose remember the last state and our state we'll never lose the state.
    val state = remember { mutableStateOf("") } // This empty string "" is initial state when
    // this function gets called for the first time, later as we update the state "remember" will
    // remember the state.

    // This method will get called again during every recomposition.
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
            Log.d("SHUBZ_LOGS", it)
        },
        label = { Text(text = "Enter message") }
    )
}
