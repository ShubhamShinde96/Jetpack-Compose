package com.example.hoistingunidirectionalflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bundle saves only primitive types, if any other type of data to be saved it needs to
        // be serialised or parceled.
        setContent {
            NotificationScreen()
        }
    }
}

@Preview
@Composable
fun NotificationScreen() { // Now that we have maintained the state int this parent composable, this is statefull composable fun.
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        var count = rememberSaveable {
            mutableStateOf(0)
        } // This is called state hoisting (hoisting state in the parent composable fun to share the
        // same state between it's multiple child composable functions.
        NotificationCounter(count.value) { count.value++ }
        MessageBar(count.value)
    }
}

@Composable
fun NotificationCounter(count: Int, increment: () -> Unit) {
//    var count = remember {
//    var count = rememberSaveable { // Read line no 67 comment.
//        mutableStateOf(0)
//    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You have sent ${count} notifications")
        // Here yo can see this is not recomposing UI after clicking on button
        // The reason is compose works on state object, the state we use in compose
        // is an observable state. So if you use state then compose will observe
        // that state and does recomposition based on state change.
        Button(onClick = {
//            count++ // as we can't update the count like this here anymore, we'll use lambda fun to do this.
            increment() // calling high lambda
            Log.d("SHUBZ_LOG", "Button Clicked")
        }) {
            Text(text = "Send Notifications")
        }
    }
}
// even if we use remember to keep our state once the app rotates (activity/fragment) gets recreated
// the composition happens newly hence even remember can't hold our state in that case.
// Solution: We need to use "rememberSaveable", what this does is whatever state we have it stores it
// inside a bundle and this is same bundle which gets passed in our onCreate() as a parameter.

// In case of view sty;e, we used to save this state in ViewModel to avoid losing state during
// Activity/Fragment configuration changes. but here also we'll use ViewModel and we'll manage our
// state through ViewModel.
// but without ViewModel we can maintain our state using "remember" and "rememberSaveable"

@Composable
fun MessageBar(count: Int) { // Now that we have kept the state in parent composable, this is now state-less composable fun

    // Now lets think if we need to update the count here, the same count that we are updating in
    // composable fun NotificationCounter(). So how can we do that. We need to share the same state
    // between these 2 components.
    // Here what we'll do is we'll define our state in the parent composable function of these 2
    // functions and that is NotificationScreen() & this is called State Hoisting.
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
            Text(text = "Message sent so far - $count")
        }
    }
}


// Unidirectional flow: This means the state data will flow from top level (Parent) composable
// to it's  child composable functions & the events(lambda function in this case) will go from child
// to parent & all this is called Unidirectional Flow.


