package com.example.shrinkwrap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrinkwrap.ui.theme.ShrinkWrapTestAppTheme
import shrinkwrap.compose.*

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShrinkWrapTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name! How are you today?",
            modifier = modifier.widthIn(0.dp, 140.dp).background(Color.Cyan),
            shrinkWrap = false
        )
        Text(
            text = "Hello $name! How are you today?",
            modifier = modifier.widthIn(0.dp, 140.dp).background(Color.Green),
            shrinkWrap = true
        )
        ShrinkWrap { measureText, onTextLayout ->
            Text(
                text = "Hello $name! How are you today?",
                modifier = modifier.widthIn(0.dp, 140.dp).background(Color.Magenta).layout(measureText),
                onTextLayout = onTextLayout,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShrinkWrapTestAppTheme {
        Greeting("Android")
    }
}