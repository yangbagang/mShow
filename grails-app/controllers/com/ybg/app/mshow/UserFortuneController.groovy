package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class UserFortuneController {

    /**
     * 获取用户财富数据
     * @param token
     * @return
     */
    def getUserFortuneByToken(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def userFortune = UserFortune.findByUserBase(userBase)
                if (!userFortune) {
                    userFortune = new UserFortune()
                    userFortune.userBase = userBase
                }

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = userFortune
            } else {
                map.isSuccess = false
                map.message = "用户不存在"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 获取用户财富数据
     * @param token
     * @return
     */
    def getUserFortuneByUserId(Long userId) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (userBase) {
            def userFortune = UserFortune.findByUserBase(userBase)
            if (!userFortune) {
                userFortune = new UserFortune()
                userFortune.userBase = userBase
            }

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userFortune
        } else {
            map.isSuccess = false
            map.message = "用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

}
