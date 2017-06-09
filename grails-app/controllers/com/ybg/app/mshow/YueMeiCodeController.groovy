package com.ybg.app.mshow

import grails.converters.JSON

class YueMeiCodeController {

    def yueMeiCodeService

    /**
     * 批量生成悦美号
     * @param prefix 前缀
     * @param begin 开始
     * @param end 结束
     * @param length 长度
     * @return
     */
    def create(String prefix, int begin, int end, int length) {
        yueMeiCodeService.createWithPrefix(prefix, begin, end, length)

        def map = [:]
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = "true"
        render map as JSON
    }

    /**
     * 保存单个悦美号
     * @param code
     * @return
     */
    def save(String code) {
        yueMeiCodeService.createInstance(code)

        def map = [:]
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = "true"
        render map as JSON
    }
}
