package com.example.legacyviewproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legacyviewproject.ui.theme.LegacyViewProjectTheme

class ModifiersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 500)
@Composable
private fun PreviewFunction() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello",
            modifier = Modifier
                .clickable { }
                .background(Color.Blue)
                .size(200.dp)
                .padding(36.dp)
                .border(4.dp, Color.Red)
                .clip(CircleShape)
                .background(Color.Yellow)
//                .clickable {  } // because we have defined clickable after some properties
            // hence currently only yellow circle has ripple effect. This is because we have
            // defined clickable  after clip(CircleShape) and background property.
            // if we define clickable as first modifier property value then whole component
            // will get ripple effect and becomes clickable. hence commenting from here and
            // adding at top(first modifier property.

            // uncomment last clickable and comment first/top clickble and turn on interactive
            // preview mode to see the effect/behaviour/changes.
        // Note: Sequence of these modifier properties also matter for resulting output UI

        )
    }
}

// Remember: whenever you design any custom composable make sure you accept modifier
// as a parameter and assign it to component. if we do like this then that component
// can be modifier from the call site and it becomes more reusable as there might be
// different customisation requirements for the same component at different places.

@Composable
fun ListViewItemWithModifier(imgId: Int, name: String, occupation: String, modifier: Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    // Note: this modifier is the modifier which we're accepting as a parameter and we're
    // adding more customisation to it over here, so basically we're doing chaining over here.
    ) {
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "User Image",
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
        )
        Column() {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = occupation, fontWeight = FontWeight.Thin, fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
fun CircularImage() {
    Image(
        painter = painterResource(id = R.drawable.human_with_flower),
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(80.dp)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
            .shadow(5.dp),
        contentDescription = "Human Holding Flower"
    )
}



