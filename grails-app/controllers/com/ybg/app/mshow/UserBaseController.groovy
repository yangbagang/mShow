package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON
import org.apache.commons.codec.digest.DigestUtils

class UserBaseController {

    def userBaseService
    def yueMeiCodeService

    /**
     * 用户登录
     * @param mobile 手机号
     * @param password 密码
     */
    def login(String mobile, String password) {
        def map = [:]
        if (mobile && password) {
            def user = UserBase.findByMobile(mobile)
            if (!user) {
                map.isSuccess = false
                map.message = "用户名或密码错误"
                map.errorCode = "2"
                map.data = "false"
            } else {
                //生成实例
                def pwd = DigestUtils.sha256Hex(password)
                if (pwd == user.password) {
                    //构造返回数据
                    def token = UserUtil.createToken(user.id)
                    map.isSuccess = true
                    map.message = "登录完成"
                    map.errorCode = "0"
                    map.data = token
                } else {
                    map.isSuccess = false
                    map.message = "用户名或密码错误"
                    map.errorCode = "2"
                    map.data = "false"
                }
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 用户注册
     * @param mobile 手机号
     * @param password 密码
     */
    def register(String mobile, String password) {
        def map = [:]
        if (mobile && password) {
            def user = UserBase.findByMobile(mobile)
            if (user) {
                map.isSuccess = false
                map.message = "该手机己经注册"
                map.errorCode = "2"
                map.data = ""
            } else {
                //生成实例
                user = new UserBase()
                user.ymCode = Long.valueOf(yueMeiCodeService.getYMCode())
                user.nickName = "悦美" + user.ymCode
                user.ymUser = mobile
                user.mobile = mobile
                user.password = DigestUtils.sha256Hex(password)
                user.flag = 1 as Short
                user.save flush: true
                //生成扩展实例
                def userInfo = new UserInfo()
                userInfo.userBase = user
                userInfo.save flush: true
                //生成账户扩展
                def userAccount = new UserAccount()
                userAccount.userBase = user
                userAccount.save flush: true

                //构造返回数据
                def token = UserUtil.createToken(user.id)
                map.isSuccess = true
                map.message = "注册完成"
                map.errorCode = "0"
                map.data = token
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 获得用户基本信息
     * @param token 用户token
     */
    def getUserBase(String token) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userBase
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 获得用户基本信息
     * @param ymCode
     */
    def getUserBaseByYmCode(Long ymCode) {
        def map = [:]
        def userBase = UserBase.findByYmCode(ymCode)
        if (userBase) {
            def result = [:]
            result.userBase = userBase
            result.userInfo = UserInfo.findByUserBase(userBase)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 获得用户ID
     * @param ymCode
     */
    def getUserIdByYmCode(Long ymCode) {
        def map = [:]
        def userBase = UserBase.findByYmCode(ymCode)
        if (userBase) {
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "${userBase.id}"
        } else {
            map.isSuccess = false
            map.message = "用户不存在"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 补充资料
     * @param token 用户token
     * @param birthday 生日
     * @param nickName 呢称
     * @param sex 性别。1为男0为女
     * @param avatar 用户头像字符串（上传到文件服务器的返回字符串）
     * @return
     */
    def completeData(String token, String birthday, String nickName, Integer sex, String avatar) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            userBaseService.completeUserBase(token, birthday, nickName, sex, avatar)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "true"
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 根据美秀ID获得用户基本信息
     * @param showId 美秀ID
     * @param token 用户token
     */
    def getAuthorInfoByShowId(Long showId, String token) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show) {
            def userBase = userBaseService.loadAuthorInfo(show, token)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = userBase
        } else {
            map.isSuccess = false
            map.message = "美秀不存在"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 更新用户基本资料
     * @param token 用户token
     * @param nickName 用户呢称
     * @param avatar 用户头偈
     * @param ymMemo 个性签名
     * @return
     */
    def updateUserBase(String token, String nickName, String avatar, String ymMemo) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                userBase.nickName = nickName
                if (avatar != null && avatar != "") {
                    userBase.avatar = avatar
                }
                userBase.ymMemo = ymMemo
                userBase.save flush: true

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

    /**
     * 更新App Token
     * @param userToken
     * @param appToken
     * @return
     */
    def updateAppToken(String userToken, String appToken) {
        def map = [:]
        if (UserUtil.checkToken(userToken)) {
            def userBase = UserBase.get(UserUtil.getUserId(userToken))
            if (userBase) {
                userBase.appToken = appToken
                userBase.save flush: true

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
