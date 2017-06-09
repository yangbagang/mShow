package com.ybg.app.mshow

import com.ybg.app.utils.MeiliConstant
import com.ybg.app.utils.MsgPushHelper
import com.ybg.app.utils.UserUtil
import grails.converters.JSON
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
        //计算积分，评论者获得活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_PING
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_PING, "评论", ruiShow.id, 0L)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_PING
        getMeiLi.save flush: true

        //创建历史记录
        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_PING, "评论", ruiShow.id, userBase.id)

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
        //计算积分，赞者获得亲善值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.qs = postMeiLi.qs + MeiliConstant.POST_ZAN
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_QS, MeiliConstant.POST_ZAN, "赞", ruiShow.id, 0L)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_PING
        getMeiLi.save flush: true

        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_ZAN, "赞", ruiShow.id, userBase.id)

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
        //计算积分，分享者获得活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_SHARE
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_SHARE, "分享", ruiShow.id, 0L)
        //计算积分，被评论者获得人气值
        def getMeiLi = getUserMeili(ruiShow.userBase)
        getMeiLi.rq = getMeiLi.rq + MeiliConstant.GET_SHARE
        getMeiLi.save flush: true

        MeiLiHistory.createInstance(ruiShow.userBase, MeiliConstant.MEILI_RQ, MeiliConstant.GET_SHARE, "分享", ruiShow.id, userBase.id)

        ruiShow.shareNum += 1
        ruiShow.save flush: true

        return ShowShare.countByShow(ruiShow)
    }

    def getUserMeili(UserBase userBase) {
        def meili = UserMeiLi.findByUserBase(userBase)
        if (!meili) {
            meili = new UserMeiLi()
            meili.userBase = userBase
            meili.save flush: true
        }
        meili
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

        //计算活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_PHOTO_VIEW
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_PHOTO_VIEW, "发布", show.id, 0L)
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

    def createLive(UserBase userBase, RuiBar ruiBar, String thumbnail, String event) {
        //生成实例
        def show = new RuiShow()
        show.userBase = userBase
        show.ruiBar = ruiBar
        show.thumbnail = thumbnail
        show.title = event
        show.type = Short.valueOf("3")
        show.save flush: true

        //计算活力值
        def postMeiLi = getUserMeili(userBase)
        postMeiLi.hl = postMeiLi.hl + MeiliConstant.POST_PHOTO_VIEW
        postMeiLi.save flush: true

        MeiLiHistory.createInstance(userBase, MeiliConstant.MEILI_HL, MeiliConstant.POST_PHOTO_VIEW, "发布", show.id, 0L)

        //关联话题
        def systemEvent = getEvent(event)
        if (systemEvent && systemEvent.flag == Short.valueOf("1")) {
            def ruiEvent = new RuiEvent()
            ruiEvent.event = systemEvent
            ruiEvent.show = show
            ruiEvent.save flush: true
        }
        show
    }

    def sendLiveMsg(RuiShow ruiShow, UserBase userBase, Integer flag, Integer type, String content) {
        if (flag == 1) {
            sendMsg(userBase, ruiShow.userBase, content, type)
        } else {
            def userList = ShowViewHistory.findAllByShow(ruiShow)*.userBase
            userList.each { user ->
                sendMsg(userBase, user, content, type)
            }
        }
    }

    private static sendMsg(UserBase sendUser, UserBase receiverUser, String msg, Integer type) {
        Thread.start {
            def liveMsg = [:]
            liveMsg.userBase = sendUser
            liveMsg.msg = msg
            liveMsg.type = type
            def map = [:]
            map.type = MsgPushHelper.LIVE_MSG
            map.data = liveMsg
            def content = map as JSON
            MsgPushHelper.sendMsg(receiverUser.appToken, content.toString())
        }
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

    def payShow(UserFortune userFortune, RuiShow ruiShow) {
        userFortune.meiPiao -= ruiShow.price
        userFortune.save flush: true

        def userFortuneHistory = new UserFortuneHistory()
        userFortuneHistory.userBase = userFortune.userBase
        userFortuneHistory.meiPiao = -ruiShow.price
        userFortuneHistory.reasonType = 0
        userFortuneHistory.reason = "ruiShow"
        userFortuneHistory.save flush: true
    }

}
