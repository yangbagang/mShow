package com.ybg.app.mshow

class SystemEvent {

    static constraints = {
        title nullable: true
        summary nullable: true
        action nullable: true
        fromDate nullable: true
        toDate nullable: true
    }

    String title = ""//标题
    String summary = ""//简介
    String action = ""//参与动作，如#悦美活动#
    Date createTime = new Date()
    Date fromDate
    Date toDate
    Integer hl = 0//活动值
    Short flag = 1 as Short//是否开放1开放，可以参与；0关闭，不能参与。

}
