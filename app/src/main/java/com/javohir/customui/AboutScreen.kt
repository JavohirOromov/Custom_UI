package com.javohir.customui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AccentCyan = Color(0xFF00E5FF)
private val BodyMuted = Color(0xFFB0E8FF)

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scroll),
    ) {
        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
        ) {
            Text(
                text = "← Orqaga",
                color = AccentCyan,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        }

        Text(
            text = "Custom UI",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
            ),
            color = AccentCyan,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
        )

        Text(
            text = "Neon spidometr qanday yasalgan",
            style = MaterialTheme.typography.titleMedium,
            color = BodyMuted,
            modifier = Modifier.padding(bottom = 20.dp),
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = AccentCyan.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(20.dp),
                ),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0D1117),
            shadowElevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AboutParagraph(
                    title = "Nega Canvas?",
                    body = "Android’da tayyor spidometr yo‘q edi, shuning uchun Compose’ning Canvasida o‘zim chizdim.",
                )
                AboutParagraph(
                    title = "Matematika",
                    body = "Markaz, radius va burchaklar — shkala va igna bir xil formuladan harakatlanadi.",
                )
                AboutParagraph(
                    title = "Qatlamlar tartibi",
                    body = "Avval fon va halo, keyin qizil zona, keyin chiziqlar va raqamlar, eng oxirida igna — shunda hamma nista to‘g‘ri ustma-ust chiqadi.",
                )
                AboutParagraph(
                    title = "Slider",
                    body = "Slider tezlikni beradi; qolgani shu qiymatga bog‘langan qayta chizish.",
                )
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun AboutParagraph(
    title: String,
    body: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            color = AccentCyan,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = body,
            color = Color(0xFFE8F4FC),
            fontSize = 15.sp,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start,
        )
    }
}
