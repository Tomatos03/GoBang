# GoBang

## 游戏截图
<img width="441" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/310258ce-799f-4fca-9d02-748635bbbcf8">
<br>
<img width="361" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/7fd8312f-8e67-4b2f-a354-4dc73a913a65">
<br>
<img width="363" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/fbd67042-614d-4963-bbc5-f48283479a48">
<br>
<img width="149" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/43a4165a-cf6e-4b0a-a6fc-04531a03b5ce">
<br>
<img width="664" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/77ecbba3-9704-441d-81a7-38ab7246f87b">
<br>
<img width="662" alt="image" src="https://github.com/Tomatos03/GoBang/assets/123353009/2a1bb252-28f7-4e81-bb23-29ee35f85abc">

> **许多功能还没有完善**

## 软件包分类

### Chess包

>存放棋盘类、棋子类、裁判类

### InteractInterface包

>存放所有的界面类

### SenderReceiver包

>存放发送接收相关的类

## 已实现的功能

+ 基于UDP广播和TCP的联机对战

## 待修复的BUG

+ 进行对局时数据丢失造成少棋子现象



## 相关类及其说明

### ChessAccord

> 存放一些常用的的常数

### ChessBoard

> 棋盘类用于初始棋盘，操作棋盘

### Piece

> 棋类用于封装棋子相关属性

### Referee

> 裁判类用于判断对局情况，以及回合切换

### EnquireUI

> 询问窗口

### MainUI

> 主游戏界面窗口

### ModeSelectUI

> 模式选择窗口

### RoomSearcherUI

> 搜索窗口

### WaitConnectUI

> 等待窗口
