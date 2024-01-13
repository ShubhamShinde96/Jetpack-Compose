package com.example.disposableeffect

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.disposableeffect.ui.theme.DisposableEffectTheme

class MainActivity : ComponentActivity() {

    // The kind of side effects where cleanup is required, at those places we use DisposableEffect.
    // For ex: if we have executed SideEffect inside a composable but when that composable gets out
    // of our screen/ when that composable leaves it's composition then we want to do some cleanup,
    // for that purpose we use this disposable effect.
    // Means a side effect which requires some cleanup before leaving the composition, for that
    // we will use DisposableEffect.

    // For ex: suppose we have used location service inside our composable so we want to dispose the
    // location service object when our composable leaves it's composition.
    // Basically we execute cleanup code logic inside DisposableEffect.

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisposableEffectTheme {
//                App()
//                MediaComposable()
                KeyboardComposable()
                TextField(value = "", onValueChange = {})
            }
        }
    }
}

@Composable
fun App() {
    var state = remember {
        mutableStateOf(false)
    }
    
    DisposableEffect(key1 = state.value) {// Similar to LaunchEffect this block
        // will execute on initial composition of this composable and then later whenever the value
        // of this key1 gets changed.
        // When key value gets changed, it cancels whatever task you are having and then it will
        // perform the cleanup & it will again execute this DisposableEffect (See logs to get exact idea).
        Log.d("DISPOSABLE_EFFECT", "Disposable effect started.")
        onDispose {
            // So whatever objects we want to dispose, we'll dispose them over here inside this
            // onDispose().
            Log.d("DISPOSABLE_EFFECT", "Cleaning up side effects.")
        }
    }
    
    Button(onClick = { state.value = !state.value }) {
        Text(text = "Change state")
    }

    // We got logs like
    // On app launch (on initial composition of this composable):
    // com.example.disposableeffect     Disposable effect started.

    // On Change State button click:
    // com.example.disposableeffect     Cleaning up side effects.
    // com.example.disposableeffect     Disposable effect started.
}


// Example
@Composable
fun MediaComposable() {
    val context = LocalContext.current // check theme project to see more on accessing context like this.
    DisposableEffect(key1 = Unit) {// We have passed unit to call this function
        // only one time during the initial composition.
        var mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.calm_wawes)
        mediaPlayer.start()
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}

// Example 2
@Composable
fun KeyboardComposable() {
    val view = LocalView.current // We are accessing current view using LocalView.current
    DisposableEffect(key1 = Unit) {
        // Whenever any change happen in our view, this listener will get called.
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val insets = ViewCompat.getRootWindowInsets(view) // This will get how many rectangles
            // are there in view and those rectangles are called as insets.
            val isKeyboardVisible =
                insets?.isVisible(WindowInsetsCompat.Type.ime()) // This ime represents the keyboard.
            Log.d("DISPOSABLE_EFFECT", isKeyboardVisible.toString()) // Check logs by opening and
            // closing the keyboard. Click on InputField to do that.
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            // So in this way whatever listeners you have, you can remove them to avoid memory leak.
        }
    }

}

