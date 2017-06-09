package com.ybg.app.mshow

class UserAccount {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        totalMoney nullable: true
        restMoney nullable: true
        usedMoney nullable: true
    }

    Float totalMoney = 0//全部金额
    Float restMoney = 0//可用金额
    Float usedMoney = 0//己使用金额
}
