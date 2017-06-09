package com.ybg.app.mshow

class MeiLiHistory {

    static belongsTo = [userBase: UserBase]

    static constraints = {
    }

    Date createTime = new Date()
    Short type//类型1=人气值， 2=活动值， 3=亲善值
    Integer score = 0//得分
    String reason = ""//原因
    Long showId = 0//指向美秀ID
    Long fromUserId = 0//由谁贡献，计算蜜爱依据。

    static createInstance(UserBase userBase, Short type, Integer score, String reason, Long showId, Long fromUserId) {
        def meiLiHistory = new MeiLiHistory()
        meiLiHistory.userBase = userBase
        meiLiHistory.type = type
        meiLiHistory.score = score
        meiLiHistory.reason = reason
        meiLiHistory.showId = showId
        meiLiHistory.fromUserId = fromUserId
        meiLiHistory.save flush: true
        meiLiHistory
    }
}
