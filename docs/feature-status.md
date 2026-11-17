# 当前能力状态

更新：2026-11-17，补DSF正常能力启用与重启实机；正式签名包另验。本表维护“后端、正式界面、验证”三层状态；当前基线看[开发接续](next-session-handoff.md)，本版范围看[v0.3说明](android-v0.3-release.md)。界面已接不等于实机全过，验证列的日期记录只证明当次包与样本；其中旧记录的“待CC／未安装”属于历史阶段，不撤销本表的源码接线状态。用户已验收任务2／9，其他设备边界继续保留。

| 能力 | 后端现状 | 正式界面 | 验证边界与接口 |
| --- | --- | --- | --- |
| DSF／DSD本地播放 | 正常生产能力已开启；DSD64／128／256单／双声道到176.4kHz PCM，共用预览／导入／来源／服务判断 | 复用既有本地导入播放；DSF恢复提示／专用展示未接，已发布v0.3不含本轮代码 | [DSF正常生产启用验收](backend-dsf-enablement-20261117.md)：64／128 COPY／REFERENCE及force-stop后四条单条队列控制／曲末通过；原数据保全。release编译／Lint／隔离／签名门禁通过，正式签名包与正式包实机未完成。前轮真实整首、256、切换／长播、管道／实际provider、断电证据见该记录；单声道未独立实机，更多设备／数小时Doze／云端按需 |
| 格式与输出信息 | 第三批后端完成；FLAC／WAV查询、Media3输入与AudioTrack配置、音质偏好复核 | 正式UI未接；FLAC／96k24 WAV／48k MP3实际事件已补验 | [格式合同](backend-audio-format-info-20261114.md)、[实机](backend-audio-expansion-device-20261114.md)；停止清理通过，decoded与deviceOutput未知，路由／原样输出另验 |
| CUE解析／分曲 | 只读预览、原子入库、共享来源、段内队列／进度／备份后端完成；首版单FLAC | 正式UI未接；系统SAF、24轨COPY／REFERENCE、恢复／撤权、分段循环／定时与共享删除已补实机 | [预览](backend-cue-preview-20261114.md)、[第二批合同](backend-cue-import-playback-20261114.md)、[实机](backend-audio-expansion-device-20261114.md)；无缝听感／多文件／长后台／分曲导出另验 |
| 锁屏歌词 C | 三态互斥／迁移、共享歌词及已应用播放页壁纸、权限／触发状态、抽象宿主与debug探针 | 用户要求本轮不接正式前端，release无具体页面 | 统一2,784项全过／Lint0错误149警告；PJF110双权限允许后同包后台显示／焦点及触摸退出回系统锁屏通过，用户确认看到；已恢复CARD／armed=false。正式页面、四方向与长后台待验；[合同](frontend-backend-contract-lockscreen-lyrics.md)、[记录](backend-lockscreen-lyrics-c-20261113.md) |
| 三平台账号/曲库/搜索 | 已接账号、资料、分页及缓存 | 已有主要入口 | 部分 Android 16 样本通过；登录全流程/跨账号仍待补。[P1](frontend-backend-contract-p1.md)、[设置等增量](backend-batch4-contract.md) |
| 手动封面裁切/选图方向 | 可选摆正后0～1区域、区域采样解码、八种EXIF方向；null整图并摆正，64px裁切下限，一次开流/暂存清理 | 本地条目已接选图／裁切／恢复封面；真机坐标与格式待验 | 定向70项、主目录统一2,510项、Lint0错误/132警告、主包/设备测试包编译及源码冻结通过；实机手势/预览坐标、HEIC、峰值内存及锁屏待验。[需求与交付](backend-requests-20261112.md#交付说明) |
| 歌曲推荐 | 网易云/酷狗每日歌曲、QQ猜你喜欢；按账号缓存、刷新/取消和完整快照点播；新增默认开启的总开关、关闭失效/清三平台推荐缓存、点播拒绝 | 已接推荐卡片／列表／开关，按kind区分每日与猜你喜欢；真实可播放性待验 | 总开关专项84项、主目录统一2,604项通过，Lint0错误135警告/主包及测试包编译/源码冻结通过；[本轮交付](backend-requests-20261112-4.md#交付说明)、[推荐合同](frontend-backend-contract-recommendations.md)。原协议组合快照见[前轮](backend-recommendations-20261112.md)。正式开关、真实账号响应/可播放性及同包听音待验 |
| 歌单内搜索与缓存补全 | 三平台可取消逐页补全、进度、共享限速/退避、最多 5000 首与容量保护已实现；搜索仍读同一缓存 | 已接搜索框、载入全部／进度与取消 | JVM 边界与文件缓存验证范围见[需求单交付](backend-requests-20261111-3.md#交付说明)；真实大歌单、页面取消/断网与搜索点播待验，队列入参须为 offset 0 连续前缀 |
| 播放/平台队列/换源/音质/输出 | 同一 Media3 服务、revision、首批先播与后台补队列、保守换源 | 已接主要操作 | 历史通知、短锁屏及换源有实际样本；多档音质、长后台、来电/回收/跨系统待专项。[P2](frontend-backend-contract-p2.md)、[换源](frontend-backend-contract-b4.md)、[音质](frontend-backend-contract-quality.md) |
| 歌单管理 | 三平台归属增删／创建／部分改名；统一目录分页与Musxi内持久排序、行权限、写互斥及三平台普通自建删单／完整核实已接，酷狗delete=true | 任务9长按爱心“收藏到”已验收；曲库新建／改名／删单及拖动已接统一catalog并整合当前DSF；真实写入与拖动体验待验 | [11-17前端接入修复](frontend-playlist-review-20261117.md)后最终同包三平台目录首页／非空详情通过；酷狗完整分页与刷新见[刷新专项](backend-playlist-refresh-20261117.md)，本地库恢复；统一2,986项、Lint0错误152警告。真实页面写入及拖动仍待；历史后端依据：[QQ／网易云生产创建和删单](backend-playlist-lifecycle-device-20261113.md)实机各1次创建／删除成功、精确ID及空内容核实、列表3→4→3／2→3→2、原行指纹一致；本轮统一2,754项全过、Lint0错误139警告／935条源码冻结通过，同主包已装机。[酷狗删除实样](backend-kugou-playlist-delete-probe-20261113.md)含1首歌、1次请求成功；[正式后端](backend-kugou-playlist-delete-20261113.md)已接，酷狗生产入口未再删。正式菜单／跨账号仍待验，QQ旧不确定marker不改写，订阅等边界见[合同](playlist-management-contract.md) |
| 行级双语与逐字歌词 | 三平台行级、KRC/QRC/YRC 字词、缓存与来源守卫已接；公开播放取样 elapsedRealtime、真实 speed 和推算门禁 | 已接逐字高亮与elapsedRealtime播放头；真人听音待验 | 11-07 单设备实际字词/译文与缓存通过，11-11 接口新增检查见[交付](backend-requests-20261111.md#验证范围与证据)，逐字听音未做。[合同](frontend-backend-contract-lyrics-translation.md)、[KRC](lyrics-word-timing-20261107.md)、[QRC](lyrics-qq-qrc-20261107.md)、[YRC](lyrics-netease-yrc-20261107.md) |
| 媒体卡片标题歌词（方案 B） | 默认关闭的持久化开关、复用歌词、服务按行调度与平台标题恢复已实现 | 已接默认关闭的设置开关 | 本包尚未安装；系统卡片显示、蓝牙／车机与 A／B 同开待验收；不依赖 ColorOS 厂商 API，不承诺所有系统渲染一致。[接口与验证](backend-media-card-lyrics-20261111.md) |
| 独立播放页/界面壁纸及取景 | 两份独立状态/草稿/清单、受管整图与中心/zoom、应用/取消、共享池/串行回收、chooseSaved；强度放宽0～150、命令边界校验，旧清单原样读取 | 已接两份壁纸／菜单／取景／预览路由，强度读取MAX_STRENGTH | 强度放宽专项56项、主目录统一2,622项通过，Lint0错误135警告/主包及测试包编译/源码一致性通过，见[本轮交付](backend-requests-20261112-5.md#交付说明)。双份壁纸前轮专项57项/统一2,575项见[记录](backend-requests-20261112-3.md#验证范围)。本轮不安装手机，两份页面/手势/取景适配与100以上文字可读性待CC同包验收；[合同](frontend-backend-contract-wallpaper.md) |
| 定时停止 | 分钟、当前曲末、分钟到点后曲末三种服务策略 | 已接分钟／当前曲末／到点后曲末三种选择 | 11-10 真实服务三种模式与单曲循环通过；11-11 长熄屏观察采样停更、后续定时未启动，仍未通过。[合同](frontend-backend-contract-sleep-timer.md)、[11-10记录](backend-local-device-20261110.md)、[补验](backend-local-device-20261111.md) |
| 本地库与导入 | COPY 默认/REFERENCE 可选；文件夹、别名、排序、进度/书签、多选/目录单次导入、持久目录关联与手动刷新、取消/重关联 | 已接本地分区、文件夹、复制／引用导入、目录关联和继续播放 | 11-10 标准 SAF、复制/引用及权限恢复通过；11-11 OPPO 两项导入与真正取消复验通过。裸 AAC 准确 seek、真人听音、OEM目录与其他提供方仍待。[合同](frontend-backend-contract-local-audio.md)、[首轮](backend-local-device-20261110.md)、[补验](backend-local-device-20261111.md) |
| 本地检索与批量整理 | 元数据检索、范围／进度／书签等筛选、revision 分页；批量加入／移出／移动与全文件夹规则排序 | 已接多选加入／移出／移动；检索筛选与规则排序入口待接 | 自动化见[交付](backend-local-organization-delivery-20261109.md)；11-11 真实SQLite 5项隔离专项复验通过，实际页面待验。[合同](frontend-backend-contract-local-audio.md)、[补验](backend-local-device-20261111.md) |
| 本地视频声音与自动封面 | 视频候选须有音轨、VIDEO 类型与 schema v3 迁移；显式禁视频轨、原容器保留；内嵌图/有界视频截帧、共享资源回收、旧条目手动分批补图与媒体封面已实现 | 已接本地内容与自动封面；视频画面未做 | 本轮 JVM/统一验证见[交付说明](backend-requests-20261111-4.md#验证范围与证据)。未安装手机，实际视频声音/截帧、OEM选择器和锁屏封面待验；不复用旧音频包结论 |
| 本地倍速、封面模式与手动图、书签改名 | 七档条目速度持久化/pitch1/平台1倍；AUTO/OFF仅隐藏自动图、手动图最高优先、清除回落；schema v4与书签改名已实现，偏好/媒体封面实时更新 | 已接倍速／书签／手动封面裁切及封面偏好／补图入口 | 本轮[交付](backend-requests-20261111-5.md#交付说明)与验证索引独立；未安装手机，真实听音不变调、OEM选图、锁屏与后台定时待验 |
| 本地来源检查 | 用户选中 1～500 条手动探测，副本／引用轻量读取、缺失／权限／变化分类、取消与迟到结果守卫，共用播放规则 | 已接条目／多选检查来源、重授权与重新关联 | 11-10 文件删除后保守READ_FAILED、真实授权撤回/恢复及重关联通过；其他提供方与同大小替换限制仍在。[合同](frontend-backend-contract-local-audio.md)、[设备记录](backend-local-device-20261110.md) |
| 本地播放与恢复 | 同一服务、本地独立身份/票据、文件夹续播、长时位置、临时队列编辑与暂停恢复 | 已接同一播放器、继续播放和从头播放 | 11-10 解码/长seek、force-stop暂停恢复及65秒后台通过；11-11 通知控制复验通过，约416秒熄屏采样不足以判定连续通过。重启、长锁屏与真人听音仍待。[首轮](backend-local-device-20261110.md)、[补验](backend-local-device-20261111.md)、[恢复](backend-local-resume-delivery-20261108.md) |
| 本地导出与整理备份 | 原始音频导出；有版本／校验的整理备份，预览后事务合并，保留当前数据 | 后台已实现，正式导出／备份入口待接 | 11-10 标准SAF导出哈希核对、整理备份预览/合并实机通过；其他提供方待验，备份不含音频和系统授权。[合同](frontend-backend-contract-local-audio.md)、[设备记录](backend-local-device-20261110.md) |
| 设置/缓存/应用内更新 | 已接统一设置、主题色名称持久化、有限缓存清理、更新校验与安装流程 | 已接主题色持久化、主要设置及更新面板 | 主题色接口与 JVM/SharedPreferences 检查见[交付](backend-requests-20261111.md#验证范围与证据)，正式页面重启待验；历史独立双版本安装有样本，正式线上更新另验。[设置](backend-batch4-contract.md)、[更新](android-app-updates.md)、[正式构建](release-build.md) |
| 交互/玻璃材质/最近播放等 | 现有状态和部分数据接口可用 | 任务2可打断的封面／歌词转场及底部三工具反馈，11-13用户验收通过；其余继续完善 | 此次验收包与自动验证见[当前交接](next-session-handoff.md)，玻璃性能、旧系统回退与完整页面待验。[交互规范](design/interaction-spec.md) |

## 后端剩余优先级

1. 用户安排近期后端优先、前端暂缓，新建歌单浮卡只记录风格待办。本地先完整拆整理／进度 revision 与查询 token；进度无关查询保持稳定，进度相关查询仍正确过期，不能只取消 saveProgress 递增。再补目录显示名、目录级错误与逐项失败、imports/sourceChecks 的 runId／完成／清除生命周期。完整既有缺口见[七项排期](backend-requests-20261111.md#本地音频七项状态与排期)。
2. 播放侧优先调查偶发启动超时并补脱敏诊断，分窗口验证真实后台定时、焦点／蓝牙中断及更长 Doze；保留已有 DSF 验收，正式 Activity 长 COPY 临时权限与正式签名包另验。稳定性之后按使用需要选 A／B、本地 LRC 或复杂 CUE；具体交付与验收见[后端建议](backend-next-priorities-20261117.md)。第五／第六批继续暂缓。
3. 歌单保留会话、刷新一致性与不确定结果对账；QQ／网易云普通自建创建及删除已于11-13补验，旧QQ不确定marker仍不可自动重试。网易云整单订阅v2与酷狗整单订阅是独立协议缺口；当前不自动进行平台写入。

已实现数据接口的细节统一写专门合同，本表只更新能力状态；旧测试数量和逐轮修复留在[证据索引](history/README.md)，不追加到当前表后形成时间线。
