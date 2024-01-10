package com.example.rememberupdatedstate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberupdatedstate.ui.theme.RememberUpdatedstateTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberUpdatedstateTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {

    // RememberUpdatedState is not a side effect, it is a composable to manage the state inside
    // SideEffect.

    val counter = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        counter.value = 10
    }
    // Counter(counter.value)
    CounterWithRememberUpdatedState(value = counter.value)
}

@Composable
fun Counter(value: Int) {
    // LaunchedEffect(key1 = value) { if I do this then also we will get the updated value but this
    // is not our solution, because we don't want this LaunchedEffect to run again and perform
    // the long running task again and again whenever value of param "value" gets changed.
    LaunchedEffect(key1 = Unit) {

        delay(5000) // Here we're mimicking a long running task.
        Log.d("REMEMBER_UPDATED_STATE", value.toString()) // And we want to use this value after
        // the long running task.
        // Now if you see, it will print 0 on screen and after 2 seconds it will print 10 but after
        // that after 5 seconds you'll get log printed of 0. but the thing is our value has updated to
        // 10 but why we're getting 0 over here? the answer is Counter() is called from outside
        // LaunchedEffect block so it's being called with initial value of 0 since the LaunchedEffect
        // block is waiting for 2 seconds to increment the counter value.
        // And LaunchedEffect will not be called again as LaunchedEffect does not get called on
        // recomposition, it only gets called on the initial composition of key1 value change.

        // But let's suppose we want to use updated value of state and not a stale value.
        // whenever value of key1 gets satisfied then it cancels the current CoroutineScope and
        // launches new scope.
        // To always get the updated state we have "rememberUpdatedState" and this make sure that you'll
        // always get updated/latest state in case if you're performing long running task.
    }
    Text(text = value.toString())
}


@Composable
fun CounterWithRememberUpdatedState(value: Int) {

    val state = rememberUpdatedState(newValue = value) // This will remember whatever value that will come.

    LaunchedEffect(key1 = Unit) {

        delay(5000)
        Log.d("REMEMBER_UPDATED_STATE", state.value.toString()) // and here we'll always get the updated
        // value in "state".

    }
    Text(text = value.toString())
}
