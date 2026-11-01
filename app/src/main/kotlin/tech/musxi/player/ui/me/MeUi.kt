package tech.musxi.player.ui.me

import tech.musxi.core.account.Platform
import tech.musxi.core.playback.PreferredAudioQuality
import tech.musxi.core.settings.LyricsFontSize
import tech.musxi.core.settings.ThemeMode
import tech.musxi.player.ui.theme.Accent

/** Membership mark next to a platform name. Unknown membership shows no mark, so it has no value here. */
enum class MembershipBadge { MEMBER, NON_MEMBER }

/**
 * One platform line of the "账号" group, derived by [meAccountRowOf]. Plain data with no Compose
 * types, so every account phase can be tested without a screen.
 */
data class MeAccountRow(
    val platform: Platform,
    /** Verified nickname. Null while the platform has not told us who is signed in. */
    val name: String?,
    /** Plain sentence after the nickname, or alone when there is none ("未连接"). */
    val hint: String?,
    val badge: MembershipBadge?,
    /** Short state at the right edge. Null when [hint] already says it. */
    val status: String?,
    /** True when the user has to act (expired session, network error): [status] uses the error colour. */
    val attention: Boolean,
)

data class MePlaybackUi(
    val autoSwitchSource: Boolean = true,
    val relaxDuration: Boolean = false,
    val quality: PreferredAudioQuality = PreferredAudioQuality.STANDARD,
)

data class MeLyricsUi(
    val followCover: Boolean = true,
    val showTranslation: Boolean = true,
    val fontSize: LyricsFontSize = LyricsFontSize.MEDIUM,
)

data class MeAppearanceUi(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val transparency: Boolean = true,
    val accent: Accent = Accent.MIST,
)

/** [cacheText] is ready to print ("128 MB", "计算中…"); [clearable] is false while there is nothing to clear or a job runs. */
data class MeStorageUi(
    val cacheText: String = "未知",
    val clearable: Boolean = true,
)

/** Everything the "我的" page shows. Built from backend state by [meUiOf]; never written back. */
data class MeUi(
    val accounts: List<MeAccountRow> = emptyList(),
    val playback: MePlaybackUi = MePlaybackUi(),
    val lyrics: MeLyricsUi = MeLyricsUi(),
    val appearance: MeAppearanceUi = MeAppearanceUi(),
    val storage: MeStorageUi = MeStorageUi(),
)

/**
 * Command callbacks of the page. The page keeps no copy of any setting: a switch shows [MeUi] and
 * reports the value the user asked for, so a failed save simply leaves the switch where it was.
 * The `onOpen…` callbacks push a sub-page; asking before [onClearCache] runs is the caller's job.
 */
class MeActions(
    val onOpenAccount: (Platform) -> Unit = {},
    val onSetAutoSwitchSource: (Boolean) -> Unit = {},
    val onSetRelaxDuration: (Boolean) -> Unit = {},
    val onOpenQuality: () -> Unit = {},
    val onSetFollowCover: (Boolean) -> Unit = {},
    val onSetShowTranslation: (Boolean) -> Unit = {},
    val onOpenFontSize: () -> Unit = {},
    val onOpenDarkMode: () -> Unit = {},
    val onSetTransparency: (Boolean) -> Unit = {},
    val onOpenAccent: () -> Unit = {},
    val onClearCache: () -> Unit = {},
)
