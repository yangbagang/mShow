package com.ybg.app.mshow

import grails.transaction.Transactional

@Transactional
class UserLabelService {

    def list(UserBase userBase) {
        UserLabel.findAllByUserBase(userBase)*.systemLabel
    }

    def update(UserBase userBase, String[] names) {
        def userLabelList = UserLabel.findAllByUserBase(userBase)
        //删除
        userLabelList.each {
            it.delete flush: true
        }
        //新增
        for (String name : names) {
            if (name != "") {
                def userLabel = new UserLabel()
                userLabel.systemLabel = getSystemLabel(name)
                userLabel.userBase = userBase
                userLabel.save flush: true
            }
        }
    }

    static getSystemLabel(String name) {
        def systemLabel = SystemLabel.findByLabelName(name)
        if (systemLabel) {
            return systemLabel
        }
        systemLabel = new SystemLabel()
        systemLabel.labelName = name
        systemLabel.catalog = name
        systemLabel.save flush: true
        return systemLabel
    }
}
