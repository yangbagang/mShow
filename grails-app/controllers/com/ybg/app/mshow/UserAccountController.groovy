package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class UserAccountController {

    /**
     * 获得用户基本信息
     * @param token 用户token
     */
    def getInfo(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)
            def accountInfo = UserAccount.findByUserBase(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = accountInfo
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }
}
