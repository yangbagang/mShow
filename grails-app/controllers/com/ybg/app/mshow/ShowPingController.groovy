package com.ybg.app.mshow

import grails.converters.JSON

class ShowPingController {

    /**
     * 显示某美秀的全部评论
     * @param showId 美秀ID
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def pings = ShowPing.findAllByShow(show)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = pings
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
