package com.ybg.app.mshow

class UserAccountHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        fromUser nullable: true
    }

    Integer ruiMoney = 0
    String reason = ""
    Integer reasonType = 1 //1充值,2消费,3提现,4收入
    UserBase fromUser
    Date createTime = new Date()
}
