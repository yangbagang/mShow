package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class ShowBangController {

    def showBangService

    def huoYueList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = showBangService.listSumShow(pageNum, pageSize, beginTime, endTime)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    def renQiList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = showBangService.listSumView(pageNum, pageSize, beginTime, endTime)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    def shouRuList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = showBangService.listSumIncome(pageNum, pageSize, beginTime, endTime)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    def haoQiList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = showBangService.listSumOutcome(pageNum, pageSize, beginTime, endTime)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 获得用户贡献信息
     * @param userId
     * @param pageNum
     * @param pageSize
     * @param beginTime
     * @param endTime
     */
    def miAiList(Long userId, Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = showBangService.listSumMoney(userId, pageNum, pageSize, beginTime, endTime)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = data
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }

        render map as JSON
    }

}
