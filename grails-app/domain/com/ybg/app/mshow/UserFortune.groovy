package com.ybg.app.mshow

class UserFortune {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Integer meiDou = 0
    Integer meiPiao = 0
    Float money = 0f

}
