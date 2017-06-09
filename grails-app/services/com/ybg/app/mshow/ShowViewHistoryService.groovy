package com.ybg.app.mshow

import com.ybg.app.utils.MsgPushHelper
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ShowViewHistoryService {

    def create(RuiShow ruiShow, UserBase userBase) {
        def history = ShowViewHistory.findByUserBaseAndShow(userBase, ruiShow)
        if (!history) {
            history = new ShowViewHistory()
            history.show = ruiShow
            history.userBase = userBase
            history.save flush: true
            def user = userBase.copyInstance() as UserBase
            user.flag = 1 as Short
            sendMsg(ruiShow, user, 1)
        } else {
            history.flag = 1 as Short
            history.updateTime = new Date()
            history.save flush: true
            def user = userBase.copyInstance() as UserBase
            user.flag = 0 as Short
            sendMsg(ruiShow, user, 1)
        }
    }

    def drop(RuiShow ruiShow, UserBase userBase) {
        def history = ShowViewHistory.findByUserBaseAndShow(userBase, ruiShow)
        if (history) {
            history.flag = 0 as Short
            history.save flush: true
            def user = userBase.copyInstance() as UserBase
            sendMsg(ruiShow, user, 0)
        }
    }

    def getViewNum(RuiShow ruiShow) {
        ShowViewHistory.countByShow(ruiShow)
    }

    def listUser(RuiShow ruiShow) {
        ShowViewHistory.findAllByShow(ruiShow)*.userBase
    }

    def hasView(UserBase userBase, RuiShow ruiShow) {
        ShowViewHistory.countByUserBaseAndShow(userBase, ruiShow) > 0
    }

    private static sendMsg(RuiShow ruiShow, UserBase userBase, Integer type) {
        Thread.start {
            ShowViewHistory.withNewSession {
                def userList = ShowViewHistory.findAllByShow(ruiShow)*.userBase
                userList.each { user ->
                    def msgType = type == 1 ? MsgPushHelper.USER_ENTER_LIVE : MsgPushHelper.USER_LEAVE_LIVE
                    def map = [:]
                    map.type = msgType
                    map.data = userBase
                    def content = map as JSON
                    MsgPushHelper.sendMsg(user.appToken, content.toString())
                }
            }
        }
    }

}
