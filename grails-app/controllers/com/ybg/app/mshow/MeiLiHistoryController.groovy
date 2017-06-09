package com.ybg.app.mshow

import grails.converters.JSON

class MeiLiHistoryController {

    def meiLiHistoryService

    /**
     * 按累计美力值排序，列出指定范围的用户及美力值
     * 返回值中data为列表，元素依次表示用户的id 头像 呢称 美力值
     * @param pageNum
     * @param pageSize
     * @param beginTime
     * @param endTime
     */
    def meiliList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = meiLiHistoryService.listSumMeili(pageNum, pageSize, beginTime, endTime)
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
     * 按累计人气排序，列出指定范围的新用户及人气值
     * 返回值中data为列表，元素依次表示用户的id 头像 呢称 人气值
     * @param pageNum
     * @param pageSize
     * @param beginTime
     * @param endTime
     */
    def renqiList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = meiLiHistoryService.listSumRenqi(pageNum, pageSize, beginTime, endTime)
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
     * 按累计活力值排序，列出指定范围的新用户及活力值
     * 返回值中data为列表，元素依次表示用户的id 头像 呢称 活力值
     * @param pageNum
     * @param pageSize
     * @param beginTime
     * @param endTime
     */
    def huoliList(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = meiLiHistoryService.listSumHuoli(pageNum, pageSize, beginTime, endTime)
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
     * 查询某人蜜爱列表
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param userId 指定用户ID
     * @return
     */
    def miAiList(Integer pageNum, Integer pageSize, String beginTime, String endTime, Long userId) {
        def map = [:]
        if (pageNum && pageSize && beginTime && endTime) {
            def data = meiLiHistoryService.listMiAi(pageNum, pageSize, beginTime, endTime, userId)
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
