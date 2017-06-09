package com.ybg.app.mshow

class UserLabel {

    static belongsTo = [userBase: UserBase, systemLabel: SystemLabel]

    static constraints = {
    }
}
