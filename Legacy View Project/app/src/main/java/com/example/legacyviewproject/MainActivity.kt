package com.example.legacyviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Button(
        onClick = {  },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, // This is for text color
            containerColor = Color.LightGray // This is for background color
        ),
        enabled = true,
        shape = CircleShape,
        modifier = Modifier.height(100.dp).width(40.dp)
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