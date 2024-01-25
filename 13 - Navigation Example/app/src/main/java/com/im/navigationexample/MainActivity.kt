package com.im.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.im.navigationexample.ui.theme.NavigationExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Defining controller: We need to define controller at top level, as it will be
            // managing everything.
            App() // Making composable named App(), we'll make it controller and this composable
            // will be controlling/driving everything.
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    // Now here we have to define 2 things: 1] Nav Host & 2] Nav Graph
    NavHost(
        navController = navController,
        startDestination = "main" // This is the start destination.
    ) {// As this (builder high order fun) is giving us option of `NavGraphBuilder`,
        // based on this our nav graph will get built.
        // There are multiple nodes inside NavGraph based on that we define the destinations.
        // So graph is collection of nodes.
        // We have function named `composable()` to define a node.
        composable(route = "registration") { // This `route` param will help us uniquely identify each node.
            RegistrationScreen() // Now here we're defining which composable screen will be rendered on this route.
            // So basically we're saying if "registration" route comes then navigate to `RegistrationScreen()`
        }
        composable(route = "login") { //
            LoginScreen()
        }
        composable(route = "main") { //
            MainScreen()
        }
        // So here we've added 3 nodes.
    }
}

@Composable
fun RegistrationScreen() {
    Text(
        text = "Registration",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun LoginScreen() {
    Text(
        text = "Login",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun MainScreen() {
    Text(
        text = "Main Screen",
        style = MaterialTheme.typography.titleLarge
    )
}

