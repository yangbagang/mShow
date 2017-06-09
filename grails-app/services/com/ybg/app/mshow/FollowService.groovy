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

}
