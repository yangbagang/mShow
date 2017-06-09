package com.ybg.app.mshow

class Follow {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase follow
}
