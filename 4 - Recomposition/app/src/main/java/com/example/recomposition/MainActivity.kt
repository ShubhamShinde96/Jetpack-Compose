package com.example.recomposition

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recomposition.ui.theme.RecompositionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

// Compositions

// 1] Initial Composition: This is when component gets loaded for the first time with whatever
// data it has loaded with initially.

// 2] Recomposition: This is when change happen in the data and compose determines that the component
// needs to be re-rendered/updated based on the latest updates on the data.
// Now here compose does not re-render all the UI, to make things efficient it only re-renders the
// component for which the corresponding data has got updates/changes.
// Here compose is smart enough to only update those particular component and keep rest components
// as it is.
// For Ex: if you have a column and have multiple text and images in that column and if state for 1 textview
// gets updated then compose will only update that textview, it will not update everything from that compose
// function.

// There are some Rules/Best-Practices which we need to follow whenever we are defining composable functions.
// 1] Make sure not to perform any heavy/long running operations in compose function, keep that on background thread.
// 2] Composable functions can be executed in any order, for ex: consider we have following composable function
/*@Composable
fun ButtonRow() {
    MyFancyNavigation {
        StartScreen()
        MiddleScreen()
        EndScreen()
    }
}*/
// This can be executed in any order, don't think that this will be executed sequentially. This might
// be executed parallel to each other or in any order even if we have written it in a sequence.
// This execution order is decided by the jetpack compose.
// So the rule is whatever functions we compose, those functions must be pure, what that means is
// that function should not have any side effect, means make sure you are not setting any outside things
// in that function, such as setting any variable value or updating something in database from that
// compose function.
// Our functions should be pure, functions should take the data that we provide them and create a UI
// from that data.

// 3] If recomposition is happening, and in the middle the state gets changed again then compose will
// stop that recomposition and start it again based on new change in the state data.

@Composable
fun Recomposable() {
    val state = remember {
        mutableStateOf(0.0)
    }
    Log.d("SHUBZ_LOG", "Logged during initial composition")
    Button(onClick = {
        state.value = Math.random()
    }) {
        Log.d("SHUBZ_LOG", "Logged during bot initial composition & recomposition")
        Text(text = state.value.toString())
    }
}

// In this ex if you check logs then log at line no 72 will be printed only once during initial composition
// log at line ni 76 gets printed everytime on button click hence we can see that only certain parts which need
// updates will get updated by compose and not the entire function.