package tech.musxi.player.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import tech.musxi.core.account.Platform
import tech.musxi.player.ui.components.OpticallyCentredText
import tech.musxi.player.ui.components.PlatformTag
import tech.musxi.player.ui.theme.LocalMusxi
import tech.musxi.player.ui.theme.displayName

/**
 * The "账号" group: one line per platform, the whole line opens that account's page. Connecting,
 * re-checking and signing out live on that page, so a row carries no button of its own.
 */
@Composable
fun MeAccountsGroup(rows: List<MeAccountRow>, onOpenAccount: (Platform) -> Unit) {
    MeGroup(
        title = "账号",
        // Two explicit lines; one wrapping sentence orphaned its last word on the account page.
        footer = "账号信息仅加密保存在本机，不会上传。\n播放由本机直连平台。",
    ) {
        rows.forEachIndexed { index, row ->
            if (index > 0) MeDivider()
            MeAccountRowView(row) { onOpenAccount(row.platform) }
        }
    }
}

/**
 * Platform mark, name and membership on the first line (they wrap onto a second line at large
 * font scales instead of squeezing the name), nickname and sentence below, state and chevron at
 * the right. No fixed height: the row grows with the text.
 */
@Composable
private fun MeAccountRowView(row: MeAccountRow, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val dark = LocalMusxi.current.dark
    MeRowFrame(onClick) {
        Column(Modifier.weight(1f)) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PlatformTag(row.platform, dark, showName = false)
                    Spacer(Modifier.width(6.dp))
                    Text(row.platform.displayName, style = MaterialTheme.typography.titleSmall, color = cs.onSurface)
                }
                row.badge?.let { MembershipPill(it) }
            }
            Spacer(Modifier.height(3.dp))
            Text(
                secondLine(row, strong = cs.onSurface, quiet = cs.onSurfaceVariant),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2, overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(12.dp))
        row.status?.let { status ->
            Text(
                status, style = MaterialTheme.typography.bodyMedium,
                color = if (row.attention) cs.error else cs.onSurfaceVariant,
                maxLines = 1,
            )
            Spacer(Modifier.width(8.dp))
        }
        MeChevron()
    }
}

/** Nickname in the main colour, the sentence after it in the quiet one. */
private fun secondLine(row: MeAccountRow, strong: Color, quiet: Color): AnnotatedString =
    buildAnnotatedString {
        row.name?.let { withStyle(SpanStyle(color = strong)) { append(it) } }
        if (row.name != null && row.hint != null) withStyle(SpanStyle(color = quiet)) { append(" · ") }
        row.hint?.let { withStyle(SpanStyle(color = quiet)) { append(it) } }
    }

/** "会员" in a faint accent tint, "非会员" in a faint grey: a mark, not a banner. */
@Composable
private fun MembershipPill(badge: MembershipBadge) {
    val cs = MaterialTheme.colorScheme
    val member = badge == MembershipBadge.MEMBER
    Box(
        Modifier.clip(CircleShape)
            .background(if (member) cs.primary.copy(alpha = 0.14f) else cs.onSurface.copy(alpha = 0.07f))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        OpticallyCentredText(
            if (member) "会员" else "非会员",
            MaterialTheme.typography.labelSmall,
            color = if (member) cs.primary else cs.onSurfaceVariant,
        )
    }
}
