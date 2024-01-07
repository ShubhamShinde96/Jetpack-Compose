package com.example.sideeffects

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import com.example.sideeffects.NetworkService.fetchCategories
import com.example.sideeffects.ui.theme.SideEffectsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SideEffectsTheme {

                // As per recommendation lf jetpack compose whatever compose functions you write, those
                // must be side effect free. what that means is impact of that function code should be
                // limited to that function only, it should not manipulate any thing that belongs to the
                // outside world of that function.
                // This is because the composable functions can be called multiple times, and many times
                // recomposition happens, similarly composable functions can be executed in any order or can be
                // executed on any thread. Also composable function can be stopped composing during recomposition
                // and starts composing again based on latest state changes.

                ListComposable()
            }
        }
    }
}

var counter = 1
@Composable
fun HasSideEffect() {
    counter++ // This belongs to the outside scope of this function and we're updating it over here,
    // so this becomes a "side effect".
    // and if we access this value in any other function then it's value can be unpredictable, because
    // this compose function can be called multiple times and components in this function can be rendered
    // in any order.
    Text(text = "Shubham")
}

@Composable
fun ListComposable() {

    // Now there are 2 problems in this composable function.
    val categoryState = remember {
        mutableStateOf(emptyList<String>())
    }
    // categoryState.value = fetchCategories() // 1st problem - we called a network call function here.
    // The thing is we don't know how many times this composable function might get called.

    // Problem 2 - if this network call takes time then our UI rendering gets delayed and UI will lag.
    // App will get frozen.

    // But sometimes we have to do such kind of operations(side effect) any way and at that time we
    // have to manage these side effect.
    // Sometime we'll have to write a code which will prevent executing such network call again and
    // again. Our composable will continue executing again and again but we'll do in such a way that
    // we'll have the control on how many times and how that network call should be called.

    // Sometimes side effects becomes important and we need this side effects to happen.
    // Suppose we are implementing back button code, now we want back button event to happen on certain
    // condition & we don't want back button event to happen over and over again. So here we want to
    // manage side effect, means that should be executed in a controlled environment.

    // 1st Side Effect handler is "LaunchEffect"
    LaunchedEffect(key1 = Unit) {
        categoryState.value = fetchCategories() // This will be executed only once.
    }
    // LaunchEffect is also a composable, now this will not generate any UI but this will execute the
    // code inside the block only once. doesn't matter how many times ListComposable() gets executed.

    // Other benefit of these EffectHandlers is this launches coroutine scope for us, like if fetchCategories()
    // take more time to fetch the data even in that case this will not block our UI, it will execute
    // the code in the background thread.

    /*LazyColumn {
        items(categoryState.value) { item ->
            Text(text = item)
        }
    }*/

    Counter()
}

@Composable
fun Counter() {
    var count = remember {
        mutableStateOf(0)
    }
    var key = count.value % 3 == 0
    // Log.d("COMPOSE-LOG", "Current count: ${count.value}") // This will get printed each time when
    // you click on button & the count will get increased each time.
//    LaunchedEffect(key1 = false) {
    LaunchedEffect(key1 = key) { // Now whenever this value of key gets changed to
        // true this block will get executed, and if it gets false then this block will not be
        // executed.
        Log.d("COMPOSE-LOG", "Current count: ${count.value}") //Now this will be executed
        // only once. Even if you click on button and increase counter even in that case this will
        // not get executed.
    }
    Button(onClick = { count.value++ }) {
        Text(text = "Increase Count")
    }
}