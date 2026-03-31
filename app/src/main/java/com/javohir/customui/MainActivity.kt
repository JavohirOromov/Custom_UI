package com.javohir.customui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.javohir.customui.ui.theme.CustomUITheme

private val SliderNeon = Color(0xFF00E5FF)
private val SliderInactiveTrack = SliderNeon.copy(alpha = 0.35f)

@Composable
private fun AppRoot(modifier: Modifier = Modifier) {
    var showAbout by remember { mutableStateOf(false) }
    if (showAbout) {
        AboutScreen(
            onBack = { showAbout = false },
            modifier = modifier,
        )
    } else {
        SpeedometerScreen(
            modifier = modifier,
            onAbout = { showAbout = true },
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomUITheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black,
                ) { innerPadding ->
                    AppRoot(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedometerScreen(
    modifier: Modifier = Modifier,
    onAbout: () -> Unit = {},
) {
    var speed by remember { mutableFloatStateOf(0f) }
    val interactionSource = remember { MutableInteractionSource() }
    val sliderColors = SliderDefaults.colors(
        thumbColor = SliderNeon,
        activeTrackColor = SliderNeon,
        inactiveTrackColor = SliderInactiveTrack,
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent,
    )
    Column(
        modifier = modifier.padding(horizontal = 24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = onAbout) {
                Text(
                    text = "About",
                    color = SliderNeon,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        Speedometer(speed = speed)
        Text(
            text = "${speed.toInt()} km/h",
            modifier = Modifier.padding(top = 12.dp),
            color = SliderNeon,
        )
        Slider(
            value = speed,
            onValueChange = { speed = it },
            valueRange = 0f..220f,
            steps = 219,
            interactionSource = interactionSource,
            colors = sliderColors,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    modifier = Modifier.shadow(
                        elevation = 6.dp,
                        shape = CircleShape,
                        spotColor = SliderNeon.copy(alpha = 0.5f),
                        ambientColor = SliderNeon.copy(alpha = 0.3f),
                    ),
                    colors = sliderColors,
                    enabled = true,
                    thumbSize = DpSize(26.dp, 26.dp),
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    colors = sliderColors,
                    enabled = true,
                )
            },
        )
        Spacer(Modifier.padding(top = 24.dp))
        Text(
            text = "Javohir Oromov",
            fontSize = 32.sp,
            color = SliderNeon,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedometerPreview() {
    CustomUITheme {
        SpeedometerScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    CustomUITheme {
        AboutScreen(onBack = {})
    }
}