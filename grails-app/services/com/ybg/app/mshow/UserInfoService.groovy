package com.ybg.app.mshow

import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class UserInfoService {

    def updateUserInfo(UserInfo userInfo, String birthday, String position, Integer bodyHigh,
                       Integer bodyWeight, String cupSize, Integer bust, Integer waist, Integer hips,
                       String province, String city) {
        def userInfoChanged = false
        //生日
        try {
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            if (birthday && birthday.length() == 10) {
                userInfo.birthday = sdf.parse(birthday)
                userInfoChanged = true
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        //职业
        if (position != null && position != "") {
            userInfo.position = position
            userInfoChanged = true
        }
        //身高
        if (bodyHigh != 0) {
            userInfo.bodyHigh = bodyHigh
            userInfoChanged = true
        }
        //体重
        if (bodyWeight != 0) {
            userInfo.bodyWeight = bodyWeight
            userInfoChanged = true
        }
        //罩杯
        if (cupSize != null && cupSize != "") {
            userInfo.cupSize = cupSize
            userInfoChanged = true
        }
        //胸围
        if (bust != 0) {
            userInfo.bust = bust
            userInfoChanged = true
        }
        //腰围
        if (waist != 0) {
            userInfo.waist = waist
            userInfoChanged = true
        }
        //臀围
        if (hips != 0) {
            userInfo.hips = hips
            userInfoChanged = true
        }
        //省份
        if (province != null && province != "") {
            userInfo.province = province
            userInfoChanged = true
        }
        //市
        if (city != null && city != "") {
            userInfo.city = city
            userInfoChanged = true
        }
        if (userInfoChanged) {
            userInfo.save flush: true
        }
    }
}
