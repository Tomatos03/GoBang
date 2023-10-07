<div align="center">
  <img src="https://pic3.zhimg.com/v2-f4810ae0a4ce545505d795d6b5b4c0bf_1440w.jpg?source=172ae18b" width="150" height="100" alt="项目图标"/>
  <h1 align="center">局域网五子棋游戏</h1>
</div>

## 项目介绍

基于 Java Swing框架实现的局域网五子棋游戏，主要目的在于学习和巩固Java相关知识


## 项目依赖
* JDK8+

## 包结构

```
src/
├── ApplicationBoot.java          // 程序启动入口
├── entity/
│   ├── ChessBoard.java          // 棋盘基础逻辑实现
│   ├── LanChessBoard.java       // 网络对战棋盘扩展逻辑
│   └── Piece.java               // 棋子数据模型
├── network/
│   ├── Message.java             // 网络消息协议定义
│   └── NetworkHandler.java      // 网络通信核心处理
└── ui/
    ├── CreateRoomPanel.java     // 创建房间界面
    ├── JoinRoomPanel.java       // 加入房间界面
    ├── LANCombatPanel.java      // 网络对战主界面
    ├── MainFrame.java           // 程序主窗口
    └── ModeSelectPanel.java     // 模式选择界面
```
## 运行截图
<img src="./images/4.png">

<img src="./images/1.png">

<img src="./images/2.png">

<img src="./images/3.png">

<img src="./images/5.png">
