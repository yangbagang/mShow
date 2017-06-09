package com.ybg.app.mshow

class YueMeiCode {

    static constraints = {
        code unique: true
    }

    String code
    Short flag = 1 as Short //1可用，0己使用
    Short hidden = 0 as Short //0未隐藏，1隐藏。特殊扩展使用
}
