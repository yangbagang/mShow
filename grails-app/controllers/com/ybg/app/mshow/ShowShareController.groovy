package com.ybg.app.mshow

import grails.converters.JSON

class ShowShareController {

    /**
     * 显示某美秀的全部分享
     * @param showId 美秀ID
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def shares = ShowShare.findAllByShow(show)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = shares
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
