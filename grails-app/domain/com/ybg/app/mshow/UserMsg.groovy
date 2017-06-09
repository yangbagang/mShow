package com.ybg.app.mshow

class UserMsg {

    static belongsTo = [msgUser: UserBase]

    static constraints = {
    }

    UserBase userBase
    Integer type
    String msg
    Date time
    Integer targetType = 1
    Long targetId
    Short hasRead = 0 as Short
}
