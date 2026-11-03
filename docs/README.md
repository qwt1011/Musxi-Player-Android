# Musxi Android 文档入口

> **状态：全量文档导航，2026-11-03。** 判断当前状态先读 [当前开发接续](next-session-handoff.md)；需要查接口以“现行接口合同”中的专门文档为准。带日期的需求、交付和验收保留当次证据，不自动代表当前基线、当前优先级或全部功能已完成。

## 当前入口

- [当前开发接续](next-session-handoff.md)：当前已核实现状、下一步和验证边界，是唯一的现状入口。
- [前后端开发交接入口](development-handoff.md)：历史入口重定向。
- [Android v0.2](android-v0.2-release.md)：正式发布说明、安装和已声明范围。
- [正式签名与 Release 构建](release-build.md)：正式签名与构建说明。
- [可复用验证工作流](validation-workflow.md)：本地/CI 入口、报告与边界。
- [GitHub Actions 远端首跑](actions-first-run-20261103.md)：实际失败、短路径修复、通过轮次与报告核对记录。
- [发布签名与私钥](signing-security.md)：公开证书、私钥保管及升级约束。
- [MeiloX 与 Glasmio 参考阅读](reference-review-20261103.md)：固定快照及可借鉴实现。
- [PC 能力对齐与后续推进](pc-capability-roadmap.md)：静态背景首版建议、已有能力收尾与实机基线顺序；尚未开始实现。

## 现行接口合同

以下文档说明前后端接口、状态和接线约定。它们仍可作为实现依据；文内的早期增量、旧数量或阶段结论按其日期解释。当前产品完成度仍以[当前开发接续](next-session-handoff.md)为准。

- [前端接入约定](frontend-contract.md)：v0.2 入口和合同优先级；旧阶段描述独立归档。
- [安卓前端所需后端增量](frontend-backend-contract-v2.md)：2026-10-20 v2 增量合同。
- [P1 后端接线](frontend-backend-contract-p1.md)：P1 数据与系统接线合同，含日期补记。
- [P2 收藏同步与队列编辑](frontend-backend-contract-p2.md)：收藏、用户队列与 revision 合同。
- [歌词翻译接线合同](frontend-backend-contract-lyrics-translation.md)：三平台歌词与译文结构、刷新和失败语义。
- [B4 保守自动换源匹配](frontend-backend-contract-b4.md)：候选资格、匹配和后台限制合同。
- [音质选择合同](frontend-backend-contract-quality.md)：逐曲音质、会员和输出兼容语义。
- [第四批后端接口合同](backend-batch4-contract.md)：设置、会员、搜索、缓存与换源等增量接口。
- [安卓应用内更新接口](android-app-updates.md)：更新检查、下载、校验和安装状态合同。

## 历史需求与旧规划

- [安卓端适配规划](../安卓端适配规划.md)：根目录保留的 2026-10-23 旧规划，不作为当前排期。

这些文件记录当时提出的需求和方案。保留原范围以核对设计和实现，不再直接驱动当前排期；项目完成度必须查对应交付、验收和[当前开发接续](next-session-handoff.md)。

- [后端需求单 2026-10-27](backend-requests-20261027.md)
- [后端需求单 2026-11-01](backend-requests-20261101.md)
- [后端需求单第二批 2026-11-01](backend-requests-20261101-2.md)
- [后端需求单第三批：正式签名与应用内更新](backend-requests-20261101-3.md)
- [后端需求单第四批：换源与交互配套](backend-requests-20261101-4.md)
- [后端需求单第五批：v0.2 收尾](backend-requests-20261101-5.md)
- [v0.1 开发基础](v0.1-foundation.md)：v0.1 增量与当时缺口。
- [歌词翻译后端路线与当前进度](lyrics-translation-plan.md)：2026-10-22 路线与阶段进度。
- [QQ 桌面与安卓播放能力对照](qq-playback-parity.md)：2026-10-19 调查和后续实验设计。
- [跨平台选择音源：首版实现与验收](cross-platform-sources.md)：首版行为、B4 更新和当时设备证据。
- [B1/B2/B3/B4 待补实测登记](backend-device-followup.md)：2026-10-22 的专项登记。

## 日期验收与交付记录

以下记录只对应文中包、提交、日期和场景。原文哈希、测试数、警告数、队列和账号状态均是当次证据；不能据此声称当前代码或正式包已复验。

- [安卓 v0.1 集成与验收](android-0.1-acceptance.md)
- [播放器基础使用验收](playback-acceptance.md)
- [P0 后端真机验收（2026-10-20）](p0-device-acceptance.md)
- [P1 队列恢复、最近播放与歌词验收（2026-10-20）](p1-data-acceptance.md)
- [P1 随机/循环验收（2026-10-20）](p1-playback-modes-acceptance.md)
- [P2 收藏同步与队列编辑验收（2026-10-20）](p2-acceptance.md)
- [后端增量 PR 记录（2026-10-21）](backend-pr-20261021.md)
- [后端 B1/B2/B3 可靠性记录（2026-10-21）](backend-reliability-20261021.md)
- [后端现状（更新至 2026-10-23）](backend-status-20261022.md)：带日期的状态快照。
- [AUDIO 调度隔离与晚间第二阶段补验](backend-audio-lane-acceptance-20261022.md)
- [B1-08 前台资格门禁与设备回归](backend-b108-acceptance-20261022.md)
- [B1-08 后台换源补验（2026-10-23）](backend-b108-alpha01-acceptance-20261023.md)
- [B4 保守匹配与系统专项](backend-b4-system-acceptance-20261022.md)
- [2026-10-22 晚间后端补验与分阶段译文](backend-evening-acceptance-20261022.md)
- [歌词翻译第二阶段与设备补验（2026-10-22）](lyrics-translation-acceptance-20261022.md)
- [酷狗 KRC 翻译接入与设备验收（2026-10-22）](lyrics-translation-kugou-acceptance-20261022.md)
- [QQ 歌词翻译接入与设备验收（2026-10-22）](lyrics-translation-qq-acceptance-20261022.md)
- [2026-10-27 晚间真机验收](backend-acceptance-20261027.md)
- [2026-10-27 队列补全、音质、输出](backend-delivery-20261027.md)
- [2026-10-27 换源提速与真机验收](source-switch-speed-20261027.md)
- [QQ 网页登录验证（2026-10-28）](qq-web-login-validation-20261028.md)
- [酷狗网页登录验证（2026-10-28）](kugou-web-login-validation-20261028.md)
- [2026-10-31 蓝牙输出补充验收](output-acceptance-20261031.md)
- [2026-10-31 后端追加实机检查](backend-followup-20261031.md)
- [2026-11-01 后端交付与验证](backend-delivery-20261101.md)
- [2026-11-01 后端真机验收](backend-live-acceptance-20261101.md)
- [第二批后端交付（2026-11-01）](backend-batch2-delivery-20261101.md)
- [第三批：发布签名与应用内更新交付](backend-batch3-delivery-20261101.md)
- [第三批真机追加验收](backend-batch3-device-acceptance-20261101.md)
- [第四批后端交付与验收边界](backend-batch4-delivery-20261101.md)
- [第四批会员与真机追加验收](backend-batch4-device-acceptance-20261101.md)
- [第五批后端交付（2026-11-01）](backend-batch5-delivery-20261101.md)
- [缓存维护追加交付（2026-11-01）](backend-cache-delivery-20261101.md)

## 设计资料

- [交互与材质规范（2026-11-01，草案 v1）](design/interaction-spec.md)：现行设计方向，文档存在不代表所列实验已全部完成。
- [播放器与歌词设计](design/player-sheets.md)：2026-10-23 设计与样张说明。
- [账号页样张与实现（2026-10-21）](design/account-sheets.md)
- [搜索页样张与实现（2026-10-21）](design/search-sheets.md)
- [应用外壳样张（2026-10-21）](design/shell-sheets.md)
- [主题样张（2026-10-21）](design/theme-sheets.md)

## 历史交接归档

- [截至 2026-10-28 的接口总览原文](history/frontend-contract-through-20261028.md)：保留原始接口说明及阶段增量，当前接入使用上面的总览。

- [截至 2026-11-01 的当前开发接续原文](history/next-session-handoff-through-20261101.md)：逐字保留当时全文，含当时的队列快照、未提交/未发布说明和阶段待办。
- [截至 2026-10-23 的前后端开发交接原文](history/development-handoff-through-20261023.md)：逐字保留旧入口全文。
