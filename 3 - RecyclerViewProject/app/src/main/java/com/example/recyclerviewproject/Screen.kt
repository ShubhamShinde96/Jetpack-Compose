package com.example.recyclerviewproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items


@Preview(heightDp = 500)
@Composable
fun PreviewItem() {
    /*Column(
        modifier = Modifier.verticalScroll(rememberScrollState()) // rememberScrollState is same like remember
    // it will keep the state of scroll maintained whenever recomposition happens.
    ) {
        getCategoryList().map { item ->
            BlogCategory(
                img = item.img,
                title = item.title,
                subtitle = item.subtitle
            )
        }
    }*/
    // Now this will load all the item at the same time, unlike recycler view. Hence commenting this.
    // To make items recyclable we need to use lazyColumn

    // "LazyColumn" to display list vertically, to display horizontal way you need to use "LazyRow"
    LazyColumn(
        // in this content we have the option of item. Whatever items we want we'll pass inside content
        // and the content will loop over items and it'll give us individual items & we'll have to tell
        // which composable we want to render for that individual item.
        content = {
            // if item is expecting int as a param when you pass the list, simply add correct import androidx.compose.foundation.lazy.items
            items(getCategoryList()) { item ->
                BlogCategory(
                    img = item.img,
                    title = item.title,
                    subtitle = item.subtitle
                )
            }
        }
    )
}


@Composable
fun BlogCategory(img: Int, title: String, subtitle: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.padding(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id =  img),
                contentDescription = "Android Icon",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .weight(.2f) // This is similar to the linear layout weight we used to
                // define to make space distributed in percentage between views.
            )
            ItemDescription(title, subtitle, Modifier.weight(.8f))
        }
    }
}

@Composable
private fun ItemDescription(title: String, subtitle: String, modifier: Modifier) {
    Column(
        modifier = modifier
        // 20% for the Image.
    ) {
        Text(
            text = title,
//                    fontWeight = FontWeight.Bold
            style = MaterialTheme.typography.labelLarge // To make design/theme of consistent styling
        )
        Text(
            text = subtitle,
//                    fontWeight = FontWeight.Thin,
//                    fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Thin

        )
    }
}

data class Category(val img: Int, val title: String, val subtitle: String)

fun getCategoryList() = mutableListOf<Category>().apply {
    add(Category(R.drawable.ic_android_black_24dp, "Shubham Shinde", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Sujay Salunkhe", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Parag Mahajan", "Senior Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Krishnendu Manna", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Manasi Pawar", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Kiran Punwatkar", "Snior Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Shubham Shinde", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Sujay Salunkhe", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Parag Mahajan", "Senior Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Krishnendu Manna", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Manasi Pawar", "Software Engineer"))
    add(Category(R.drawable.ic_android_black_24dp, "Kiran Punwatkar", "Snior Software Engineer"))
}
