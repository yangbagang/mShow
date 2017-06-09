package com.ybg.app.mshow

class ShowShare {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    Date createTime = new Date()
    UserBase userBase

}
