# yuanshenMusicRobot

原琴机器人

乐谱易编写，通俗易懂

## musicscores.txt说明

###全局配置   

default_play :

    说明 : 每个乐谱是否默认播放
    范围 : {true, false}
    默认值 : false

start_delay :

    说明 : 开始播放之前延迟多少毫秒
    scope : {0~INT_MAX}
    default : 0

play_mode :

    说明 : 播放模式，（进入程序后只加载一次）
        single：单曲模式，播放1首曲子后退出程序；
        hotkey：热键模式， --f5：开始/停止播放， --f11：退出程序
    scope : {single, hotkey}
    default : hotkey

### 局部（每首曲子）配置   （每次 f5 播放时加载）

play:
    
    说明 : 该曲目是否播放
    范围 : {true, false}
    默认值 : false

speed:
    
    说明 : 播放速度，即每拍的时值（ms）
    范围 : {1~INT_MAX}
    默认值 : 500

### 局部（每个声部）配置   （每次 f5 播放时加载）

{+8}、{-8}:

    说明 ：该声部整体升高/降低8度