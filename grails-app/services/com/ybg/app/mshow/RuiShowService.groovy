package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.transaction.Transactional

@Transactional
class RuiShowService {

    def ping(RuiShow ruiShow, UserBase userBase, String content) {
        //生成评论实例
        def showPing = new ShowPing()
        showPing.show = ruiShow
        showPing.createTime = new Date()
        showPing.userBase = userBase
        showPing.ping = content
        showPing.save flush: true

        //增加评论数量
        ruiShow.pingNum += 1
        ruiShow.save flush: true

        return ShowPing.countByShow(ruiShow)
    }

    def zan(RuiShow ruiShow, UserBase userBase) {
        //生成赞实例
        def showZan = new ShowZan()
        showZan.show = ruiShow
        showZan.createTime = new Date()
        showZan.userBase = userBase
        showZan.save flush: true

        ruiShow.zanNum += 1
        ruiShow.save flush: true

        return ShowZan.countByShow(ruiShow)
    }

    def share(RuiShow ruiShow, UserBase userBase) {
        //生成分享实例
        def showShare = new ShowShare()
        showShare.show = ruiShow
        showShare.createTime = new Date()
        showShare.userBase = userBase
        showShare.save flush: true

        ruiShow.shareNum += 1
        ruiShow.save flush: true

        return ShowShare.countByShow(ruiShow)
    }

    def create(UserBase userBase, String thumbnail, String title, Short type, Integer price) {
        //生成实例
        def show = new RuiShow()
        show.userBase = userBase
        show.thumbnail = thumbnail
        show.title = title
        show.type = type
        show.price = price
        show.save flush: true

        show
    }

    def getActionNum(RuiShow ruiShow, String token) {
        def map = [:]
        map.pingNum = ShowPing.countByShow(ruiShow)
        map.zanNum = ShowZan.countByShow(ruiShow)
        map.shareNum = ShowShare.countByShow(ruiShow)
        if (UserUtil.checkToken(token)) {
            def userId = UserUtil.getUserId(token)
            def userBase = UserBase.get(userId)
            if (userBase) {
                map.hasPing = ShowPing.countByShowAndUserBase(ruiShow, userBase)
                map.hasZan = ShowZan.countByShowAndUserBase(ruiShow, userBase)
                map.hasShare = ShowShare.countByShowAndUserBase(ruiShow, userBase)
            } else {
                map.hasPing = 0
                map.hasZan = 0
                map.hasShare = 0
            }
        } else {
            map.hasPing = 0
            map.hasZan = 0
            map.hasShare = 0
        }
        map
    }

    private static getEvent(String event) {
        def systemEvent = SystemEvent.findByAction(event)
        if (systemEvent == null) {
            systemEvent = new SystemEvent()
            systemEvent.action = event
            systemEvent.save flush: true
        }
        systemEvent
    }

    def payShow(UserAccount userAccount, RuiShow ruiShow) {
        //消费端减可用秀币
        userAccount.restMoney -= ruiShow.price
        userAccount.usedMoney += ruiShow.price
        userAccount.save flush: true
        //消费端记录消费历史
        def userAccountHistory = new UserAccountHistory()
        userAccountHistory.userBase = userAccount.userBase
        userAccountHistory.ruiMoney = -ruiShow.price
        userAccountHistory.reason = "消费"
        userAccountHistory.reasonType = 2
        userAccountHistory.save flush: true
        //发布者增加可用秀币
        def money = getIncomeNum(ruiShow.price)
        def postAccount = UserAccount.findByUserBase(ruiShow.userBase)
        postAccount.totalMoney += money
        postAccount.restMoney += money
        postAccount.save flush: true
        //发布者增加秀币记录
        def postAccountHistory = new UserAccountHistory()
        postAccountHistory.userBase = userAccount.userBase
        postAccountHistory.ruiMoney = money
        postAccountHistory.reason = "收入"
        postAccountHistory.reasonType = 4
        postAccountHistory.fromUser = userAccount.userBase
        postAccountHistory.save flush: true
    }

    private Integer getIncomeNum(Integer price) {
        def showConfigure = ShowConfigure.get(1)
        if (showConfigure) {
            return price*(1 - showConfigure.platformScale).intValue()
        }
        return price
    }
}
