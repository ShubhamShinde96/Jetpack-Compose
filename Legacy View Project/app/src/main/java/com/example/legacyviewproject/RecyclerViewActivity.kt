package com.example.legacyviewproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legacyviewproject.ui.theme.LegacyViewProjectTheme

class RecyclerViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComposable() {

    Column() {
        ListViewItem(
            R.drawable.user_icon,
            "Shubham Shinde",
            "Software Engineer"
        )
        ListViewItem(
            R.drawable.user_icon,
            "Sujay Salunkhe",
            "Software Engineer"
        )
        ListViewItem(
            R.drawable.user_icon,
            "Parag Mahajan",
            "Software Engineer"
        )
        ListViewItem(
            R.drawable.user_icon,
            "Krishnendu Manna",
            "Software Engineer"
        )
        ListViewItem(
            R.drawable.user_icon,
            "Manasi Pawar",
            "Software Engineer"
        )
    }

}

@Composable
fun ListViewItem(imgId: Int, name: String, occupation: String) {
    Row(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
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