package com.ybg.app.mshow

import com.ybg.app.utils.CaptchaUtil
import grails.converters.JSON

class SystemController {

    /**
     * 获取验证码
     * @param mobile 手机号
     */
    def getCaptcha(String mobile) {
        def map = [:]
        if (mobile) {
            map.isSuccess = true
            map.message = "验证码己经发送"
            map.errorCode = "0"
            def captcha = CaptchaUtil.createCaptcha(mobile)
            map.data = captcha
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 检查验证码是否正确
     * @param mobile 手机号
     * @param captcha 码证码
     */
    def checkCaptcha(String mobile, String captcha) {
        def map = [:]
        int result = CaptchaUtil.checkCaptcha(mobile, captcha)
        if (result == 0) {
            map.isSuccess = true
            map.message = "验证码正确"
            map.errorCode = "0"
            map.data = "true"
        } else if (result == 1) {
            map.isSuccess = false
            map.message = "验证码己过期"
            map.errorCode = "1"
            map.data = "false"
        } else {
            map.isSuccess = true
            map.message = "验证码错误"
            map.errorCode = "2"
            map.data = "false"
        }
        render map as JSON
    }
}
