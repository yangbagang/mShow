package com.ybg.app.mshow

import grails.converters.JSON

class UserAccountHistoryController {

    def listIncome(Long userId, Integer pageNum, Integer pageSize) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (pageNum && pageSize && userBase) {
            def c = UserAccount.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("userBase", userBase)
                order("createTime", "desc")
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    def listOutcome(Long userId, Integer pageNum, Integer pageSize) {
        def map = [:]
        def userBase = UserBase.get(userId)
        if (pageNum && pageSize && userBase) {
            def c = UserAccount.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("fromUser", userBase)
                order("createTime", "desc")
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

}
