package tech.musxi.player.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import tech.musxi.player.ui.components.pressableRow
import tech.musxi.player.ui.theme.Haptic
import tech.musxi.player.ui.theme.LocalMusxi
import tech.musxi.player.ui.theme.MusxiShapes
import tech.musxi.player.ui.theme.rememberHaptics

/** Inner side padding of a card. Rows bleed their press tile out by the same amount, so it reaches the card edge. */
internal val CardInset = 16.dp

/**
 * One group of a grouped list: a small grey title above, a rounded card of rows, an optional grey
 * footer below. A null [title] leaves the group without a heading (the 关于 group draws its own).
 *
 * The light card is almost the page colour, so it gets a hairline edge; in dark mode the card is a
 * step above the page and needs none.
 */
@Composable
fun MeGroup(
    title: String?,
    modifier: Modifier = Modifier,
    footer: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    val dark = LocalMusxi.current.dark
    Column(modifier.fillMaxWidth()) {
        if (title != null) {
            Text(
                title, style = MaterialTheme.typography.labelMedium, color = cs.onSurfaceVariant,
                modifier = Modifier.padding(start = CardInset, bottom = 8.dp),
            )
        }
        Column(
            Modifier.fillMaxWidth()
                .clip(MusxiShapes.large)
                .background(meCardColor())
                .then(
                    if (dark) Modifier
                    else Modifier.border(0.5.dp, cs.outlineVariant.copy(alpha = 0.6f), MusxiShapes.large),
                )
                .padding(horizontal = CardInset),
            content = content,
        )
        if (footer != null) {
            Text(
                footer, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant,
                modifier = Modifier.padding(start = CardInset, end = CardInset, top = 8.dp),
            )
        }
    }
}

/** Hairline between two rows of one card. */
@Composable
fun MeDivider() {
    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
}

/**
 * The frame every row shares: at least 52dp tall, 10dp above and below its content, so a row grows
 * with the font scale instead of clipping. A non-null [onClick] makes the whole row one press
 * tile; the tile spans the card's full width.
 */
@Composable
fun MeRowFrame(
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    role: Role = Role.Button,
    content: @Composable RowScope.() -> Unit,
) {
    val press = if (onClick != null) {
        Modifier.pressableRow(onClick = onClick, role = role, bleed = CardInset, corner = 0.dp)
    } else Modifier
    Row(
        modifier.fillMaxWidth().then(press).heightIn(min = 52.dp).padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/** Title (with an optional line of explanation) and a switch. The row is the tap target. */
@Composable
fun MeSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    subtitle: String? = null,
) {
    val cs = MaterialTheme.colorScheme
    val haptic = rememberHaptics()
    MeRowFrame(
        onClick = {
            haptic(Haptic.TICK)
            onCheckedChange(!checked)
        },
        modifier = Modifier.semantics { stateDescription = if (checked) "已开启" else "已关闭" },
        role = Role.Switch,
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = cs.onSurface)
            if (subtitle != null) {
                Spacer(Modifier.height(3.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
            }
        }
        Spacer(Modifier.width(12.dp))
        MeSwitch(checked)
    }
}

/**
 * Title on the left, current value on the right. With a chevron it opens a sub-page; without one
 * it runs an action in place (the cache row). A null [onClick] leaves the row inert.
 * [leading] sits just before the value, for a small mark such as the accent swatch.
 */
@Composable
fun MeValueRow(
    title: String,
    value: String,
    onClick: (() -> Unit)?,
    chevron: Boolean = onClick != null,
    leading: (@Composable () -> Unit)? = null,
) {
    val cs = MaterialTheme.colorScheme
    MeRowFrame(onClick) {
        Text(
            title, Modifier.weight(1f), style = MaterialTheme.typography.titleSmall, color = cs.onSurface,
        )
        Spacer(Modifier.width(12.dp))
        if (leading != null) {
            leading()
            Spacer(Modifier.width(8.dp))
        }
        Text(
            value, style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant,
            maxLines = 1, overflow = TextOverflow.Ellipsis,
        )
        if (chevron) {
            Spacer(Modifier.width(8.dp))
            MeChevron()
        }
    }
}

/** One choice of a single-choice list: label, and a check on the chosen one. Picking the chosen row does nothing. */
@Composable
fun MeChoiceRow(label: String, selected: Boolean, onSelect: () -> Unit) {
    val haptic = rememberHaptics()
    MeRowFrame(
        onClick = {
            if (!selected) {
                haptic(Haptic.TICK)
                onSelect()
            }
        },
        modifier = Modifier.semantics { this.selected = selected },
        role = Role.RadioButton,
    ) {
        Text(
            label, Modifier.weight(1f), style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (selected) MeCheck()
    }
}
