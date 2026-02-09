package no.sebbe.fnkotlin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

import fnkotlin.composeapp.generated.resources.Res
import fnkotlin.composeapp.generated.resources.compose_multiplatform

@Composable
fun Greeting(name:String) {
    Text(text = "Halla",
        color = Color.Blue,
        fontSize = 30.sp
    )
}

@Composable
fun PreviewGreeting() {
    Greeting("Android")
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Text("+")
            }
        }
        ) {
        }
    }
}