package com.ybg.app.mshow

class UserAchievement {

    static belongsTo = [userBase: UserBase, achievement: SystemAchievement]

    static constraints = {
    }
}
