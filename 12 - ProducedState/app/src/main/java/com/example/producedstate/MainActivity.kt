package com.example.producedstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.producedstate.ui.theme.ProducedStateTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProducedStateTheme {
//                CounterWithProducedState()
//                AnimateLoader()
                UsingDerivedStateExample()
            }
        }
    }
}

@Composable
fun Counter() {
    val state = remember {
        mutableStateOf(0) // let's suppose if I want to update this state asynchronously then
        // 1st] we were defining state like this.
    }

    LaunchedEffect(key1 = Unit) {
        for (i in  1..10) {
            delay(1000)
            state.value++
        }
    } //2nd] we're using "LaunchedEffect" / "RememberCoroutineScope" and inside this we're updating
    // the state under CoroutineScope block.
    Text(
        text = state.value.toString(),
        style = MaterialTheme.typography.headlineLarge
    )

    // To combine both these operations into one we have utility method called "ProduceState" which
    // will produce a state for us and whatever changes we'll make in that state that can be updated
    // asynchronously.

    // It will be useful where we will have a liveData and we want to define state based on that
    // liveData or it will be useful where we have object of flow and it is continuously having
    // changes in it's values and based on that we want to update the state. So in such cases we will
    // be using producedState and inside that we will be accessing flow or liveData and whatever updates
    // that will happen in our flow or liveData that will keep being updated in our state automatically
    // with the help of producedState and we don't have to write any extra code to update the state.
}


// Converting above function into producedState
@Composable
fun CounterWithProducedState() {
//    val state = remember {
    val state = produceState(initialValue = 0) {// here we're passing the initial
        // value.
        for (i in  1..10) {
            delay(1000)
            value++ // here we just have to directly access the value, as it's inside the state block
            // itself.

            // so basically produceState is producing state for us and inside the block we're defining
            // how and when the state should be updated.
            // and we can perform asynchronous operations here just like LaunchedEffect.
        }
    }
    Text(
        text = state.value.toString(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun Loader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(1f),
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                )
                Text(text = "Loading")
            }
        }
    )
}

// we'll try to animate above composable
@Composable
fun AnimateLoader() {

    val degree = produceState(initialValue = 0) {
        while (true) {
            delay(16)
            value = (value + 10) % 360 // circle is of 360 degree hence we have added % 360 to make
            // sure the value doesn't go beyond 360.

            // The same way we will be accessing liveData or flow and the way data will get updated in
            // liveData or flow producedState will update the state and our UI will get recomposed and
            // will get updated.
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(1f),
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .rotate(degree.value.toFloat()) // We're using the state value here which is
                // produced by produceState()
                )
                Text(text = "Loading")
            }
        }
    )
}


// Concept - derivedStateOf()

@Composable
fun UsingDerivedStateExample() {

    val tableOf = remember { mutableStateOf(5) }
//    val index = remember { mutableStateOf(1) }
    val index = produceState(initialValue = 1) {
        repeat(9) {
            delay(1000)
            value++
        }
    }

    val message = remember {
        derivedStateOf {
            // What derivedStateOf does is if you have multiple objects of state and based on those
            // multiple states you want to create a single state then in that case derivedStateOf
            // comes into play.
            // As you can see from the name, this is deriving new state based on multiple state objects.
            "${tableOf.value} * ${index.value} = ${tableOf.value * index.value}"
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(1f),
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = message.value,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    )
}

// The derivedStateOf is much more than this, read below article to find out more.
// https://medium.com/androiddevelopers/jetpack-compose-when-should-i-use-derivedstateof-63ce7954c11b

