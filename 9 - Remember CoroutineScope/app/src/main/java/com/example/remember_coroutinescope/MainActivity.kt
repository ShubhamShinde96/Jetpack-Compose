package com.example.remember_coroutinescope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.remember_coroutinescope.ui.theme.RememberCoroutineScopeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberCoroutineScopeTheme {

                // Comment/UnComment to see the results.
//                LaunchEffectComposable()
                CoroutineScopeComposable()
            }
        }
    }
}

@Composable
fun LaunchEffectComposable() {
    val counter = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {// Everytime when LaunchEffect will run, it will create
        // a new coroutine scope for us. This also applies when the value of key changes and due to
        // that LaunchEffect block executes again. At that time also it will create a new CoroutineScope
        // for us.
        Log.d("REMEMBER_C_SCOPE", "Started...")
        try {
            for (i in 1..10) {
                counter.value++
                delay(1000)
            }
        } catch (e: Exception) {
            Log.d("REMEMBER_C_SCOPE", "Exception: ${e.message.toString()}") // We used to get
            // this log "Exception: StandaloneCoroutine was cancelled" but we're not getting this log anymore.
            // This was according to CeezyCode.
            // Why this was happening is: When LaunchEffect starts this creates a new coroutine for us
            // and when we rotate the device and activity gets recreated (configuration changes) then the
            // hierarchy of composable (LaunchEffectComposable()) gets recreated. When this composable
            // gets recreated then LaunchEffect will gets re-executed and that's why it disposes the
            // current CoroutineScope and all the coroutines inside this scope will get cancelled and then
            // it will create a new CoroutineScope for us.

            // This all is being done to avoid memory leak.

        }
    }

    // Now this LaunchEffect has some problems:
    // Problem No 1] this LaunchEffectComposable() is a composable and we need one more composable to run this composable fun.
    // but suppose we want to launch coroutine on some button click or on some event then in that
    // case we will not be able to use this LaunchEffect.
    
    Button(onClick = {
        //LaunchedEffect(key1 = Unit) {} // This is giving us error "@Composable invocations can only
        // happen from the context of a @Composable function" basically this is saying composable
        // functions can only run through composable only.
        // So basically we can't use LaunchEffect to launch coroutine here in this button click event.
        // So we will need some individual CoroutineScope to launch the coroutine.
    }) {

    }

    // Problem No 2]: This CoroutineScope that we are getting inside/through this LaunchedEffect that
    // is given to use by that LaunchedEffect only, hence whatever management of that CoroutineScope
    // is, means when it will start that coroutine and when it will cancel/end that coroutine that is
    // all managed by this LaunchEffect composable, we don't have any control over it. controle means
    // if we want to cancel coroutine, or we may want to delay the coroutine or we want to join some
    // coroutine so those things we can't do over here.

    // Problem/Feature No 3]: LaunchEffect run on initial composition or whenever key1 parameter gets
    // changed & when this run after that we get this CoroutineScope and we will be able to launch
    // coroutines inside that CoroutineScope but the problem is if we want to launch some independent
    // scope & to solve this problem we have a function called "rememberCoroutineScope", let's see
    // how that works.

    val scope = rememberCoroutineScope() // This will give us a scope based on that we can launch
    // new coroutines. And this will not have any impact of recomposition so whatever side effects
    // we will have those side effects will execute in a controlled environment.

    // So basically we have 2 ways to run side effects
    // 1] LaunchedEffect and 2] rememberCoroutineScope()

    // So with the help of rememberCoroutineScope() we can run the side effects and to do that we
    // will launch the coroutine inside this scope(rememberCoroutineScope()) and we'll execute the
    // side effects under that coroutine.

    // Now the thing is the scope (rememberCoroutineScope()) is attached to this composable function
    // LaunchEffectComposable() lifecycle, if this composable function (composable LaunchEffectComposable())
    // removed from UI then all the coroutines defined and running under this scope rememberCoroutineScope()
    // will get canceled. This is same as viewModelScope, as when ViewModel gets removed from memory
    // all the viewModelScope running from that ViewModel also gets cancelled.
    // We can use this scope rememberCoroutineScope() anywhere under this composable function.


    var text = "Counter is running ${counter.value}"

    if(counter.value == 10) {
        text = "Counter stopped"
    }
    Text(text = text)
}

@Composable
fun CoroutineScopeComposable() {
    val counter = remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()

    var text = "Counter is running ${counter.value}"
    if(counter.value == 10) {
        text = "Coroutine stopped"
    }
    
    Column {
        Text(text = text)
        Button(onClick = {

            scope.launch {
                Log.d("REMEMBER_C_SCOPE", "Started...")
                try {
                    for (i in 1..10) {
                        counter.value++
                        delay(1000)
                    }
                } catch (e: Exception) {
                    Log.d("REMEMBER_C_SCOPE", "Exception: ${e.message.toString()}")
                }
            }

        }) {
            Text(text = "Start")
        }

        // After clicking on start try rotating device and you'll get logs like below.
        // Started...
        // Exception: Job was cancelled

        // So as we can see we're getting exception that our job has cancelled. so this is same
        // thing that we've talked before, as our configuration got changed hence the hierarchy of
        // our composable has created again. and because the composable has recreated the job(rememberCoroutineScope())
        // that was also cancelled and all the coroutines inside that job those also got cancelled.
        // And because the composable has recreated the object of job(rememberCoroutineScope) that also
        // recreated/reinitialised ie we have fresh object of that.

    }
}

