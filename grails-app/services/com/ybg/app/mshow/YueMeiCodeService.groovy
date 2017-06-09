package com.ybg.app.mshow

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class YueMeiCodeService {

    def createWithPrefix(String prefix, int begin, int end, int length) {
        for (int i = begin; i <= end; i++) {
            def instance = new YueMeiCode()
            instance.code = formatCode(prefix, i, length)
            instance.hidden = 0 as Short
            instance.flag = 1 as Short
            instance.save(flush: true)
        }
    }

    private static String formatCode(String prefix, int value, int length) {
        String v = "${value}"
        String zero = ""
        int l = length - v.length()
        if (l > 0) {
            for (int i = 0; i < l; i++) {
                zero += "0"
            }
        }
        prefix + zero + v
    }

    def getYMCode() {
        def ymCode = YueMeiCode.findByFlagAndHidden(1 as Short, 0 as Short)
        if (ymCode) {
            ymCode.flag = 0 as Short
            ymCode.save flush: true
        }
        ymCode?.code
    }

    def createInstance(String code) {
        def instance = new YueMeiCode()
        instance.code = code
        instance.hidden = 0 as Short
        instance.flag = 1 as Short
        instance.save(flush: true)
        if (instance.hasErrors()) {
            instance.errors.each {
                println it
            }
        }
    }
}
