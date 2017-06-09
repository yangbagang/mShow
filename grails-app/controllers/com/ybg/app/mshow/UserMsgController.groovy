package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class UserMsgController {

    /**
     * 获取好友消息
     * @param token
     * @return
     */
    def getMsgList(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def msgList = UserMsg.findAllByMsgUser(userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = msgList
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
}
