#网页聊天室
**2020.05.15更新**

##所用技术
项目使用Maven构建,后台使用的语言是JAVA
框架为SpringBoot + Mybatis-plus
开发工具使用的是[IntelliJ IDEA 2019.3.3 x64]

前端运用vue3.0、webpack框架构建项目
UI框架主要是element-ui
请求使用axios
通讯使用websocket
开发工具vscode

##实现功能
用户登录,用户信息展示,用户信息修改,用户头像修改,修改密码,在线聊天,在线列表展示,日志记录展示等.

##使用方法
前端工程：chat_room
java工程：chat_room_java

数据库文件:/chat_room_java/chat_room_parent/chat_room_api/src/resources/file/webchat.sql
直接导入数据库即可

内置账号:admin/admin

修改前端工程main.js中：
axios.defaults.baseURL = 'http://localhost:9009/api/'
Vue.prototype.$wsServer = "ws://localhost:9009/ws"


项目中还有许多地方可以进行优化的，比如：用户登陆管理上我们可以用上shiro/security的安全框架、密码安全校验、websocket指定发送等等，大家可以先试着自己做，之后作者也会往这方面持续优化，也打算添加新功能，比如视频播放、直播、弹幕等等。

作者提供一个设计思想、大致实现方式供各位同学学习参考，同学们可以下载项目，按照自己的思想，继续开发，发现不对的地方欢迎指正。

