package tech.musxi.player.ui.library

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import tech.musxi.core.account.Platform
import tech.musxi.core.library.EditablePlaylist
import tech.musxi.core.library.LibraryPageFailure
import tech.musxi.core.library.MediaKey
import tech.musxi.core.library.PlaylistChangeStatus
import tech.musxi.core.library.PlaylistDeletionResult
import tech.musxi.core.library.PlaylistKind
import tech.musxi.core.library.PlaylistManagementCapabilities
import tech.musxi.core.library.PlaylistManagementInfo
import tech.musxi.core.library.PlaylistSummary

class PlaylistManagementControllerTest {
    private val summary = PlaylistSummary(MediaKey(Platform.NETEASE, "1"), "夜车", null, 8, PlaylistKind.CREATED)
    private val target = EditablePlaylist(summary, 3, false)
    private val info = PlaylistManagementInfo(target, canRename = true, canDelete = true, canUnfollow = false, reason = null)

    private class Fixture {
        val notices = mutableListOf<String>()
        val refreshes = mutableListOf<Platform>()
        val deleted = mutableListOf<MediaKey>()
        var info: ((Result<PlaylistManagementInfo>) -> Unit)? = null
        var created: ((Result<EditablePlaylist>) -> Unit)? = null
        var renamed: ((Result<Unit>) -> Unit)? = null
        var deleting: ((Result<PlaylistDeletionResult>) -> Unit)? = null
        var createCount = 0
        var renameCount = 0
        var deleteCount = 0
        val controller = PlaylistManagementController(object : PlaylistManagementActions {
            override fun capabilities(platform: Platform) = PlaylistManagementCapabilities(true, true, false, delete = true)
            override fun info(key: MediaKey, result: (Result<PlaylistManagementInfo>) -> Unit) { info = result }
            override fun create(platform: Platform, name: String, result: (Result<EditablePlaylist>) -> Unit) { createCount++; created = result }
            override fun rename(target: EditablePlaylist, name: String, result: (Result<Unit>) -> Unit) { renameCount++; renamed = result }
            override fun delete(target: EditablePlaylist, result: (Result<PlaylistDeletionResult>) -> Unit) { deleteCount++; deleting = result }
            override fun refresh(platform: Platform) { refreshes += platform }
        }, notices::add, deleted::add)
    }

    @Test fun menuWaitsForFreshRowPermission() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        assertTrue(fixture.controller.menu!!.loading)
        fixture.controller.openRename()
        fixture.controller.openDelete()
        assertNull(fixture.controller.naming)
        assertNull(fixture.controller.deleting)
        fixture.info!!(Result.success(info))
        fixture.controller.openDelete()
        assertEquals(target, fixture.controller.deleting!!.target)
    }

    @Test fun anOldAccountsPermissionCannotOpenANewMenu() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        val old = fixture.info!!
        fixture.controller.sync(Platform.NETEASE, 4)
        fixture.controller.openMenu(summary)
        old(Result.success(info))
        assertTrue(fixture.controller.menu!!.loading)
        assertNull(fixture.controller.menu!!.info)
    }

    @Test fun createUsesTheServerTitleAndCannotDoubleSubmit() {
        val fixture = Fixture()
        fixture.controller.openCreate()
        fixture.controller.submitName("本地输入")
        fixture.controller.submitName("本地输入")
        assertEquals(1, fixture.createCount)
        val actual = EditablePlaylist(summary.copy(title = "平台实际标题"), 0, false)
        fixture.created!!(Result.success(actual))
        assertEquals("已创建“平台实际标题”", fixture.notices.single())
        assertNull(fixture.controller.naming)
    }

    @Test fun unconfirmedRenameKeepsTheTextAndRefreshesWithoutRetry() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        fixture.info!!(Result.success(info))
        fixture.controller.openRename()
        fixture.controller.submitName("新名字")
        fixture.renamed!!(Result.failure(LibraryPageFailure("not_confirmed")))
        assertEquals("新名字", fixture.controller.naming!!.draft)
        assertFalse(fixture.controller.naming!!.submitting)
        assertEquals("结果未确认，请刷新核对", fixture.controller.naming!!.error)
        assertEquals(1, fixture.renameCount)
        assertEquals(listOf(Platform.NETEASE), fixture.refreshes)
    }

    @Test fun unknownDeleteDoesNotRemoveTheOpenPlaylistOrRetry() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        fixture.info!!(Result.success(info))
        fixture.controller.openDelete()
        fixture.controller.confirmDelete()
        fixture.deleting!!(Result.success(PlaylistDeletionResult(summary.key, PlaylistChangeStatus.UNKNOWN, null, "network")))
        assertTrue(fixture.deleted.isEmpty())
        assertEquals("结果未确认，请刷新核对", fixture.notices.single())
        assertEquals(1, fixture.deleteCount)
    }

    @Test fun onlyConfirmedAbsenceCompletesDeletion() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        fixture.info!!(Result.success(info))
        fixture.controller.openDelete()
        fixture.controller.confirmDelete()
        fixture.deleting!!(Result.success(PlaylistDeletionResult(summary.key, PlaylistChangeStatus.APPLIED, true)))
        assertEquals(listOf(summary.key), fixture.deleted)
        assertEquals("已删除歌单", fixture.notices.single())
    }

    @Test fun nameValidationMatchesQqAndUnicodeLimits() {
        assertEquals("请输入歌单名称", playlistNameProblem(" ", Platform.QQ))
        assertEquals("QQ 音乐歌单名称最多 20 个字符", playlistNameProblem("😀".repeat(11), Platform.QQ))
        assertNull(playlistNameProblem("😀".repeat(10), Platform.QQ))
        assertEquals("名称最多 80 个字符", playlistNameProblem("😀".repeat(81), Platform.NETEASE))
        assertEquals("名称不能包含控制字符", playlistNameProblem("a\nb", Platform.KUGOU))
    }

    @Test fun accountChangeDismissesPendingCreateAndIgnoresItsLateAnswer() {
        val fixture = Fixture()
        fixture.controller.openCreate()
        fixture.controller.submitName("旧账号操作")
        val old = fixture.created!!
        fixture.controller.sync(Platform.QQ, 5)
        assertNull(fixture.controller.naming)
        old(Result.success(target))
        assertTrue(fixture.notices.isEmpty())
        fixture.controller.openCreate()
        assertEquals(Platform.QQ, fixture.controller.naming!!.platform)
        assertFalse(fixture.controller.naming!!.submitting)
    }

    @Test fun leavingPageDismissesPendingDeleteAndIgnoresItsLateAnswer() {
        val fixture = Fixture()
        fixture.controller.openMenu(summary)
        fixture.info!!(Result.success(info))
        fixture.controller.openDelete()
        fixture.controller.confirmDelete()
        fixture.controller.pageChanged()
        assertNull(fixture.controller.deleting)
        fixture.deleting!!(Result.success(PlaylistDeletionResult(summary.key, PlaylistChangeStatus.APPLIED, true)))
        assertTrue(fixture.deleted.isEmpty())
        assertTrue(fixture.notices.isEmpty())
    }

    @Test fun aPendingNameOperationCannotBeReplacedByAnotherDialog() {
        val fixture = Fixture()
        fixture.controller.openCreate()
        fixture.controller.submitName("保留本次操作")
        fixture.controller.openCreate()
        fixture.controller.openMenu(summary)
        assertTrue(fixture.controller.naming!!.submitting)
        assertNull(fixture.controller.menu)
        fixture.controller.submitName("不应重复提交")
        assertEquals(1, fixture.createCount)
    }
}
