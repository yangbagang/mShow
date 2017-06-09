package com.ybg.app.mshow

import grails.converters.JSON

class SystemEventController {

    /**
     * 显示可用话题
     * @return
     */
    def list() {
        def events = SystemEvent.findAllByFlag(1 as Short)
        def map = [:]
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = events
        render map as JSON
    }

}
