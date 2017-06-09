package com.ybg.app.mshow

class RuiBar {

    static constraints = {
        thumbnail nullable: true
    }

    String name//吧名
    String thumbnail = ""//缩略图
    Short flag = 1 as Short//是否可用 1可用 0己关闭
}
