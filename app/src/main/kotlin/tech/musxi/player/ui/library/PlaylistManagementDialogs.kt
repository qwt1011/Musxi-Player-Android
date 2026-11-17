package tech.musxi.player.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import tech.musxi.player.ui.components.AnchoredMenu
import tech.musxi.player.ui.components.MenuDivider
import tech.musxi.player.ui.components.MenuHeader
import tech.musxi.player.ui.components.MenuItem
import tech.musxi.player.ui.components.ProgressArc
import tech.musxi.player.ui.theme.displayName
import androidx.compose.ui.unit.dp

@Composable
fun PlaylistManagementMenu(
    ui: PlaylistMenuUi,
    onDismiss: () -> Unit,
    onRename: () -> Unit,
    onDelete: () -> Unit,
    onMoveToStart: () -> Unit,
    onMoveToEnd: () -> Unit,
    onResetOrder: () -> Unit,
    canMove: Boolean,
    canMoveToEnd: Boolean,
    customOrder: Boolean,
    enabled: Boolean,
) {
    AnchoredMenu(expanded = true, onDismiss = onDismiss, preferAbove = false) {
        MenuHeader(ui.playlist.title, "排序仅保存在 Musxi")
        MenuItem("移到最前", onMoveToStart, enabled = enabled && canMove)
        MenuItem("移到最后", onMoveToEnd, enabled = enabled && canMoveToEnd,
            supporting = if (canMoveToEnd) null else "请先加载完整列表")
        if (customOrder) MenuItem("恢复默认顺序", onResetOrder, enabled = enabled)
        if (ui.loading) MenuItem("正在读取操作权限…", {}, enabled = false,
            leading = { ProgressArc(color = MaterialTheme.colorScheme.onSurfaceVariant, size = 16.dp, strokeWidth = 1.5.dp) })
        ui.info?.let { info ->
            if (info.canRename || info.canDelete) MenuDivider()
            if (info.canRename) MenuItem("重命名", onRename, Icons.Outlined.DriveFileRenameOutline, enabled = enabled)
            if (info.canDelete) MenuItem("删除歌单", onDelete, Icons.Outlined.DeleteOutline,
                iconTint = MaterialTheme.colorScheme.error, enabled = enabled)
        }
        if (ui.error != null) MenuItem("操作权限读取失败", {}, enabled = false, supporting = ui.error)
    }
}

@Composable
fun PlaylistNameDialog(ui: PlaylistNameUi, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var value by remember { mutableStateOf(TextFieldValue(ui.draft, TextRange(0, ui.draft.length))) }
    val candidate = value.text.trim()
    val problem = playlistNameProblem(candidate, ui.platform)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(ui.title) },
        text = {
            Column {
                OutlinedTextField(
                    value = value, onValueChange = { value = it }, enabled = !ui.submitting,
                    singleLine = true, isError = value.text.isNotEmpty() && problem != null,
                    supportingText = {
                        Text(ui.error ?: if (value.text.isNotEmpty()) problem.orEmpty() else
                            if (ui.platform == tech.musxi.core.account.Platform.QQ) "最多 20 个字符" else "最多 80 个字符")
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(candidate) }, enabled = !ui.submitting && problem == null) {
                Text(if (ui.submitting) "正在提交…" else if (ui.target == null) "创建" else "保存")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss, enabled = !ui.submitting) { Text("取消") } },
    )
}

@Composable
fun PlaylistDeleteDialog(ui: PlaylistDeleteUi, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    val playlist = ui.target.playlist
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("删除${playlist.key.platform.displayName}歌单？") },
        text = { Text("将删除整个“${playlist.title}”，包含其中 ${playlist.trackCount} 首歌曲的歌单关系。\n\n歌曲本身和其他歌单不会删除，已经开始播放的队列继续保留。此操作会修改平台账号中的歌单。") },
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = !ui.submitting) {
                Text(if (ui.submitting) "正在删除…" else "删除整个歌单",
                    color = if (ui.submitting) androidx.compose.ui.graphics.Color.Unspecified else MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss, enabled = !ui.submitting) { Text("取消") } },
    )
}
