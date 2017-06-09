package com.ybg.app.mshow

class ShowZan {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase

    transient String nickName
    transient String avatar

    String getNickName() {
        userBase?.nickName ?: userBase?.ymCode
    }

    String getAvatar() {
        userBase?.avatar
    }
}
