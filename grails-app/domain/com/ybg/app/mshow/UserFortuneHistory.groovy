package com.ybg.app.mshow

class UserFortuneHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        targetUser nullable: true
    }

    Integer meiDou = 0
    Integer meiPiao = 0
    Float money = 0f
    Date createTime = new Date()
    UserBase targetUser
    Integer reasonType //0购买1送礼2出售9系统赠送
    String reason = ""//具体原因

}
