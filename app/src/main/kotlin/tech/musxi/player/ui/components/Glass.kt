package tech.musxi.player.ui.components

import android.content.Context
import android.os.Build
import android.os.PowerManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import tech.musxi.player.ui.theme.isDarkSurface

/**
 * Glass styles of docs/design/interaction-spec.md, section 3. REGULAR: dock, sheets, top bars.
 * THIN: floating menus and notices. CLEAR: controls over the player's cover colour, icons only.
 */
enum class GlassStyle(val blur: Dp, val lightTint: Float, val darkTint: Float, val elevation: Dp) {
    REGULAR(24.dp, 0.55f, 0.45f, 18.dp),
    THIN(20.dp, 0.68f, 0.55f, 12.dp),
    CLEAR(16.dp, 0.30f, 0.30f, 12.dp),
}

/** What a glass surface needs from its host. [blurs] is false where the solid fallback is drawn. */
class GlassScope internal constructor(
    internal val haze: HazeState,
    val blurs: Boolean,
    internal val dark: Boolean,
    internal val solid: Color,
)

/** The nearest [GlassHost]; null outside one, where glass draws as solid. */
val LocalGlass = compositionLocalOf<GlassScope?> { null }

/**
 * Records [content] as the blur source and draws [overlay] above it. Only what [content] draws can
 * show through the glass, so floating layers belong in [overlay]. The page background is recorded
 * with the content: glass must blur an opaque layer.
 *
 * Falls back to solid below Android 12 (no real-time blur), in battery saver, and when [enabled] is
 * false (the "透明效果" setting, once settings exist).
 */
@Composable
fun GlassHost(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    overlay: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    val context = LocalContext.current
    val haze = remember { HazeState() }
    val saver = remember(context) { (context.getSystemService(Context.POWER_SERVICE) as? PowerManager)?.isPowerSaveMode == true }
    val dark = cs.background.isDarkSurface()
    val scope = GlassScope(
        haze = haze,
        blurs = enabled && !saver && Build.VERSION.SDK_INT >= 31,
        dark = dark,
        solid = if (dark) cs.surfaceContainerHigh else Color.White,
    )
    CompositionLocalProvider(LocalGlass provides scope) {
        Box(modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().hazeSource(haze).background(cs.background), content = content)
            overlay()
        }
    }
}

/**
 * A glass surface: soft shadow, clip, blur with tint and grain, a top sheen, a 1dp highlight stroke.
 * With no [scope] (outside a host) or when the scope does not blur, it is the solid fallback with
 * the same shape, shadow and stroke. Pass `LocalGlass.current` as [scope].
 */
fun Modifier.glass(scope: GlassScope?, shape: Shape, style: GlassStyle = GlassStyle.REGULAR): Modifier {
    val dark = scope?.dark ?: false
    val base = shadow(
        style.elevation, shape,
        ambientColor = Color.Black.copy(alpha = 0.12f), spotColor = Color.Black.copy(alpha = 0.20f),
    ).clip(shape)
    val surface = if (scope != null && scope.blurs) {
        val tint = (if (dark) Color.Black else Color.White).copy(alpha = if (dark) style.darkTint else style.lightTint)
        base.hazeEffect(scope.haze) {
            blurRadius = style.blur
            tints = listOf(HazeTint(tint))
            noiseFactor = 0.08f
        }.drawBehind {
            drawRect(
                Brush.verticalGradient(
                    0f to Color.White.copy(alpha = if (dark) 0.16f else 0.35f),
                    0.35f to Color.Transparent,
                    0.75f to Color.Transparent,
                    1f to Color.Black.copy(alpha = if (dark) 0.14f else 0.06f),
                ),
            )
        }
    } else {
        base.background(scope?.solid ?: Color.White)
    }
    return surface.border(
        1.dp,
        Brush.linearGradient(
            listOf(
                Color.White.copy(alpha = if (dark) 0.50f else 0.85f),
                Color.White.copy(alpha = if (dark) 0.08f else 0.15f),
                Color.White.copy(alpha = if (dark) 0.28f else 0.45f),
            ),
        ),
        shape,
    )
}
