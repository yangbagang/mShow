package com.ybg.app.mshow

class UserMeiLi {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Integer rq = 0//人气值
    Integer hl = 0//活动值
    Integer qs = 0//亲善值

    transient Integer meiLi
    Integer getMeiLi() {
        rq + hl + qs
    }
}
