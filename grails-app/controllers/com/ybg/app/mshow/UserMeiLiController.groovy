package com.ybg.app.mshow

import grails.converters.JSON

class UserMeiLiController {

    /**
     * 获得指定用户的美力值
     * @param userId
     * @return
     */
    def getUserMeiLi(Long userId) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (userBase) {
            def meiLi = UserMeiLi.findByUserBase(userBase)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "${meiLi?.meiLi ?: 0}"
        } else {
            map.isSuccess = false
            map.message = "用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
