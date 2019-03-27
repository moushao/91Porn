
9*Porn Android 客户端，突破游客每天观看10次视频的限制，还可以下载视频

### 目前具备功能
1. 无限制观看（绝大部分用户）
2. 登录收藏功能
3. 下载视频
4. 额外的图片板块以及其他视频板块
5. 浏览论坛帖子功能

### 技术概览
rxjava + retrofit + rxcache + mvp + jsoup + dagger2

时间能力有限，欢迎各位提交PR

### 更新日志：

-----2019年03月22日 v1.1.0


感谢[Cabchinoe](https://github.com/Cabchinoe)提供的最新9*Porn视频地址解析方法，我个人尝试了另一种方式，即使用一个不可见的WebView加载md5.js，然后调用js解密参数也能实现地址解析
但是效率相对没有直接代码解析快，可作为备用的方式。

1. 修复9*porn视频无法播放问题 [Cabchinoe](https://github.com/Cabchinoe)
2. 修复妹子图列表无法加载问题
3. 优化论坛帖子内容图片显示问题
4. 去掉一些依赖，升级了部分库版本，最低api支持为19，即4.4以上
6. 其他改动调整及bug修复


-----2019年01月13日 v1.0.9

1. 修复9*porn无法登陆问题
2. 9*porn不用登录也可以查看作者的其他视频了
3. 修复P*gav无法播放视频问题
4. 修复九妹图社部分图集无法解析问题
5. 增加A*gle视频搜索
6. 其他改动调整及bug修复

------2018年10月4日 v1.0.8
1. 修复适配JiaoZiVideoPlayer播放引擎
2. 修复9*PORN除主页外其他分类打开崩溃的Bug

------2018年10月2日 v1.0.7

本版本代码改动较大，可能隐藏有较多的bug

1. 修复朱*力视频无法解析问题
2. 修复九妹图社无法解析图片列表图片错误问题
3. 下载增加重试下载功能[Archive94](https://github.com/Archive94)
4. 修复9*PORN登陆账号后可能会有20次观看次数限制问题[Archive94](https://github.com/Archive94)
5. 其他改动调整及bug修复

**注意：v1.0.1以下版本需要手动下载apk并卸载重新安装，否则程序崩溃且下版本无法自动更新（之前项目老版本需卸载重装）**
[更多更新日志...](https://github.com/techGay/v9porn/blob/master/UPGRADE_LOG.md)


### 安装包APK下载

程序带自动检查更新升级功能，如若不能自动更新，在[APK](https://github.com/techGay/v9porn/tree/master/apk)目录或者[released](https://github.com/techGay/v9porn/releases)界面手动下载升级即可

### 自行编译--编译环境

请用最新版AndroidStudio（至少3.0以上），个人本地基本都是有更新就更新

当前版本：

Android Studio 3.3.2

Build #AI-182.5107.16.33.5314842, built on February 16, 2019

JRE: 1.8.0_152-release-1248-b01 amd64

JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o

Windows 10 10.0

### 其他
觉得项目不错，给个start或赞赏，请我喝杯咖啡：

 微信 | 支付宝 
 ------------- | -------------
 ![赞赏](https://github.com/techGay/v9porn/blob/master/img/mm_reward_qrcode_1547141812376.png) | ![赞赏](https://github.com/techGay/v9porn/blob/master/img/alipay1547141972480.jpg) 

### 赞赏&打赏列表

[....列表](https://github.com/techGay/v9porn/blob/master/REWARD.md)

### 声明
本项目仅做技术交流使用，任何人或组织无论以何种形式将其用在其他任何地方由此引发的各种问题均与本人无关