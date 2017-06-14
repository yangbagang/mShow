package com.ybg.app.mshow

import grails.transaction.Transactional

@Transactional
class FollowService {

    def follow(UserBase userBase1, UserBase userBase2) {
        def f = Follow.findByUserBaseAndFollow(userBase1, userBase2)
        if (f == null) {
            def instance = new Follow()
            instance.userBase = userBase1
            instance.follow = userBase2
            instance.createTime = new Date()
            instance.save flush: true
        }
    }

    def getFollowList(UserBase userBase, Integer pageNum, Integer pageSize) {
        def c = Follow.createCriteria()
        def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
            eq("follow", userBase)
            order("createTime", "desc")
        }
        def list = []
        for (Follow follow in result) {
            def map = [:]
            map.id = follow.id
            map.nickName = follow.userBase.nickName
            map.avatar = follow.userBase.avatar
            map.createTime = follow.createTime
            list.add(map)
        }
        list
    }

    def getFansList(UserBase userBase, Integer pageNum, Integer pageSize) {
        def c = Follow.createCriteria()
        def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
            eq("userBase", userBase)
            order("createTime", "desc")
        }
        def list = []
        for (Follow follow in result) {
            def map = [:]
            map.id = follow.id
            map.nickName = follow.follow.nickName
            map.avatar = follow.follow.avatar
            map.createTime = follow.createTime
            list.add(map)
        }
        list
    }

}
