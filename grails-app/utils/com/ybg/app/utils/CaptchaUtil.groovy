package com.ybg.app.utils

/**
 * Created by yangbagang on 2016/11/1.
 */
class CaptchaUtil {

    private static final DEFAULT_CAPTCHA = "1234"
    private static final VALID_TIME = 1000 * 60 * 2
    private static timeContainer = [:]
    private static codeContainer = [:]

    private static isDebug = true

    static getRandomCaptcha() {
        def random = new Random()
        def captcha = ""
        for (int i = 0; i < 4; i++) {
            captcha += random.nextInt(10)
        }
        captcha
    }

    static void main(String[] args) {
        def captcha = getRandomCaptcha()
        println captcha
    }

    static createCaptcha(String mobile) {
        def time = timeContainer.mobile
        if (time) {
            def now = new Date()
            def d = now.time - time.time
            if (d < VALID_TIME) {
                return 0
            }
        }
        def now = new Date()
        def randomCaptcha = isDebug ? DEFAULT_CAPTCHA : getRandomCaptcha()
        timeContainer.mobile = now
        codeContainer.mobile = randomCaptcha
        return randomCaptcha
    }

    static checkCaptcha(String mobile, String captcha) {
        def time = timeContainer.mobile
        def code = codeContainer.mobile
        if (time && code == captcha) {
            def now = new Date()
            def d = now.time - time.time
            if (d < VALID_TIME) {
                timeContainer.remove(mobile)
                codeContainer.remove(mobile)
                return 0//存在，且合法
            }
            return 1//存在，过期
        }
        return 2//不存在
    }

}
