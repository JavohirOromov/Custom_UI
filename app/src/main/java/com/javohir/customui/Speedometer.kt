package com.javohir.customui

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val NeonCyan = Color(0xFF00E5FF)
private val NeonCyanBright = Color(0xFF4DFFFF)
private val HaloBlue = Color(0xFF4488FF)
private val RedSoft = Color(0xFFFFAB91)
private val RedStrong = Color(0xFFC40012)
private val NeedleRed = Color(0xFFFF0033)
private val BlackBg = Color(0xFF000000)

private fun redBySpeed(t: Float): Color =
    lerp(RedSoft, RedStrong, t.coerceIn(0f, 1f))

private val dialTextColor = android.graphics.Color.rgb(0, 255, 255)
private val dialTextGlowColor = android.graphics.Color.argb(180, 0, 220, 255)


@Composable
fun Speedometer(
    speed: Float,
    modifier: Modifier = Modifier,
    minSpeed: Float = 0f,
    maxSpeed: Float = 220f,
) {
    val sweepDegrees = 270f
    val startAngle = 135f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(BlackBg)
    ) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val minDim = minOf(w, h)

        val arcPad = minDim * 0.07f
        val arcSize = Size(w - 2 * arcPad, h - 2 * arcPad)
        val topLeft = Offset(arcPad, arcPad)

        val rOuter = minDim / 2f - arcPad
        val rTickOuter = rOuter * 0.94f
        val rTickInner = rOuter * 0.78f
        val rTickMinorOuter = rOuter * 0.90f
        val rTickMinorInner = rOuter * 0.84f
        val rLabel = rOuter * 0.62f

        val t = ((speed - minSpeed) / (maxSpeed - minSpeed)).coerceIn(0f, 1f)
        val sweepAngle = t * sweepDegrees

        fun degToRad(deg: Float): Float = (deg * PI / 180f).toFloat()

        fun pointOnCircle(r: Float, angleDeg: Float): Offset {
            val rad = degToRad(angleDeg)
            return Offset(cx + r * cos(rad), cy + r * sin(rad))
        }

        val rHalo = minDim * 0.5f - 2.dp.toPx()
        val haloTopLeft = Offset(cx - rHalo, cy - rHalo)
        val haloSize = Size(rHalo * 2f, rHalo * 2f)
        for (layer in 10 downTo 0) {
            val sw = (3f + layer * 3.5f).dp.toPx()
            val a = (0.03f + layer * 0.028f).coerceIn(0.02f, 0.38f)
            drawArc(
                color = HaloBlue.copy(alpha = a * 0.55f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = haloTopLeft,
                size = haloSize,
                style = Stroke(width = sw, cap = StrokeCap.Round),
            )
        }
        for (layer in 6 downTo 0) {
            val sw = (2f + layer * 2.5f).dp.toPx()
            val a = (0.05f + layer * 0.05f).coerceIn(0.05f, 0.45f)
            drawArc(
                color = NeonCyan.copy(alpha = a * 0.65f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = haloTopLeft,
                size = haloSize,
                style = Stroke(width = sw, cap = StrokeCap.Round),
            )
        }

        for (layer in 6 downTo 0) {
            val sw = (2f + layer * 2.8f).dp.toPx()
            val a = (0.06f + layer * 0.055f).coerceIn(0.05f, 0.55f)
            drawArc(
                color = NeonCyan.copy(alpha = a),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = sw, cap = StrokeCap.Round),
            )
        }
        drawArc(
            color = NeonCyanBright,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round),
        )

        val rRed = rOuter * 0.54f
        val redTopLeft = Offset(cx - rRed, cy - rRed)
        val redArcSize = Size(rRed * 2f, rRed * 2f)
        val ovalCenter = Offset(cx, cy)
        val redSweepWidth = 14.dp.toPx()
        val red = redBySpeed(t)
        val intensity = 0.35f + 0.65f * t
        if (t > 0f) {
            drawArc(
                brush = Brush.radialGradient(
                    colors = listOf(
                        red.copy(alpha = (0.10f + 0.18f * t) * intensity),
                        red.copy(alpha = 0.03f + 0.05f * t),
                    ),
                    center = ovalCenter,
                    radius = rRed * 1.15f,
                ),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = redTopLeft,
                size = redArcSize,
            )
            for (layer in 5 downTo 0) {
                val w = redSweepWidth + layer * 7.dp.toPx()
                val a = (0.05f + layer * 0.04f).coerceIn(0.04f, 0.32f) * intensity
                drawArc(
                    color = red.copy(alpha = a),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = redTopLeft,
                    size = redArcSize,
                    style = Stroke(width = w, cap = StrokeCap.Round),
                )
            }
            drawArc(
                color = red.copy(alpha = (0.42f + 0.38f * t) * intensity),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = redTopLeft,
                size = redArcSize,
                style = Stroke(width = redSweepWidth * 0.5f, cap = StrokeCap.Round),
            )
        }

        val majorCount = 11
        for (i in 0 until majorCount) {
            val a = i / majorCount.toFloat()
            val b = (i + 1) / majorCount.toFloat()
            val frac = (a + b) / 2f
            val angleDeg = startAngle + frac * sweepDegrees
            val inner = pointOnCircle(rTickMinorInner, angleDeg)
            val outer = pointOnCircle(rTickMinorOuter, angleDeg)
            drawLine(
                color = NeonCyan.copy(alpha = 0.8f),
                start = inner,
                end = outer,
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }

        for (i in 0..11) {
            val frac = i / 11f
            val angleDeg = startAngle + frac * sweepDegrees
            val inner = pointOnCircle(rTickInner, angleDeg)
            val outer = pointOnCircle(rTickOuter, angleDeg)
            drawLine(
                color = NeonCyan,
                start = inner,
                end = outer,
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = NeonCyanBright.copy(alpha = 0.35f),
                start = inner,
                end = outer,
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }

        val labelPaint = android.graphics.Paint().apply {
            color = dialTextColor
            textSize = 14.dp.toPx()
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.SANS_SERIF,
                android.graphics.Typeface.BOLD,
            )
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
            setShadowLayer(12.dp.toPx(), 0f, 0f, dialTextGlowColor)
        }
        for (i in 0..11) {
            val frac = i / 11f
            val angleDeg = startAngle + frac * sweepDegrees
            val p = pointOnCircle(rLabel, angleDeg)
            val value = i * 20
            val metrics = labelPaint.fontMetrics
            val textY = p.y - (metrics.ascent + metrics.descent) / 2f
            drawContext.canvas.nativeCanvas.drawText(
                "$value",
                p.x,
                textY,
                labelPaint,
            )
        }

        val arcAngleDeg = startAngle + sweepAngle
        rotate(degrees = arcAngleDeg - 270f, pivot = Offset(cx, cy)) {
            val needleLen = rRed * 0.92f
            drawLine(
                color = NeedleRed.copy(alpha = 0.45f),
                start = Offset(cx, cy),
                end = Offset(cx, cy - needleLen * 1.02f),
                strokeWidth = 10.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = NeedleRed,
                start = Offset(cx, cy),
                end = Offset(cx, cy - needleLen),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }

        val hubR = 12.dp.toPx()
        for (g in 3 downTo 0) {
            val mix = if (g % 2 == 0) NeonCyan else Color(0xFF9966FF)
            drawCircle(
                color = mix.copy(alpha = 0.1f + g * 0.07f),
                radius = hubR + g * 4.dp.toPx(),
                center = Offset(cx, cy),
                style = Stroke(width = 2.dp.toPx()),
            )
        }
        drawCircle(color = NeonCyanBright, radius = hubR, center = Offset(cx, cy))
        drawCircle(color = BlackBg, radius = hubR * 0.45f, center = Offset(cx, cy))

        val unitPaint = Paint().apply {
            color = dialTextColor
            textSize = 12.dp.toPx()
            typeface = Typeface.create(
                Typeface.SANS_SERIF,
                Typeface.BOLD,
            )
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            setShadowLayer(10.dp.toPx(), 0f, 0f, dialTextGlowColor)
        }
        val unitY = cy + rOuter * 0.42f
        val um = unitPaint.fontMetrics
        drawContext.canvas.nativeCanvas.drawText(
            "km/h",
            cx,
            unitY - (um.ascent + um.descent) / 2f,
            unitPaint,
        )
    }
}
