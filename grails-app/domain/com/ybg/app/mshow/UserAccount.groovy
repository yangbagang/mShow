package com.ybg.app.mshow

class UserAccount {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        totalMoney nullable: true
        restMoney nullable: true
        usedMoney nullable: true
    }

    Integer totalMoney = 0//全部金额
    Integer restMoney = 0//可用金额
    Integer usedMoney = 0//己使用金额
}
