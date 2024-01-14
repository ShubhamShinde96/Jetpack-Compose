package com.example.producedstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.producedstate.ui.theme.ProducedStateTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProducedStateTheme {
                CounterWithProducedState()
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
