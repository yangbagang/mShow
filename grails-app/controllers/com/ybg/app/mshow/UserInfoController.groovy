package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class UserInfoController {

    def userInfoService

    /**
     * 获取用户信息
     * @param token 用户token
     * @return
     */
    def getUserInfo(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)
            def userInfo = UserInfo.findByUserBase(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userInfo
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 更新用户个性信息
     * @param token 用户token
     * @param birthday 日生
     * @param sex 性别1为男0为女
     * @param position 职业
     * @param bodyHigh 身高
     * @param bodyWeight 体重
     * @param cupSize 罩杯
     * @param bust 胸围
     * @param waist 腰围
     * @param hips 臀围
     * @param province 省
     * @param city 市
     * @return
     */
    def updateUserInfo(String token, String birthday, String position, Integer bodyHigh,
                       Integer bodyWeight, String cupSize, Integer bust, Integer waist, Integer hips,
                       String province, String city) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                userInfoService.updateUserInfo(UserInfo.findByUserBase(userBase), birthday, position, bodyHigh,
                bodyWeight, cupSize, bust, waist, hips, province, city)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "true"
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
