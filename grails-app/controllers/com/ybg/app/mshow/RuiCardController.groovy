package com.ybg.app.mshow

import grails.converters.JSON

class RuiCardController {

    /**
     * 列出秀币卡，用于秀币充值
     * @return
     */
    def list() {
        def map = [:]
        def data = RuiCard.findAllByFlag(1 as Short)
        map.isSuccess = true
        map.message = ""
        map.errorCode = "0"
        map.data = data
        render map as JSON
    }

}
