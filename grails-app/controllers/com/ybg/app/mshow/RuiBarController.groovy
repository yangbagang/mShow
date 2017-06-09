package com.ybg.app.mshow

import grails.converters.JSON

class RuiBarController {

    /**
     * 显示系统内所有可用板块（吧名）
     * @return
     */
    def list() {
        def bars = RuiBar.findAllByFlag(1 as Short)
        def map = [:]
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = bars
        render map as JSON
    }
}
