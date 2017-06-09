package com.ybg.app.mshow

import grails.converters.JSON

class ShowFileController {

    def showFileService

    /**
     * 显示某个美秀全部附件
     * @param showId 美秀ID
     * @return
     */
    def list(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            def files = ShowFile.findAllByShowAndFlag(show, 1 as Short)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = files
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 为某个美秀增加图片或视频等文件。
     * @param showId 美秀ID
     * @param fileIds 附件字符串（上传到文件服务器返回的路径字符串），多个以逗号分割。
     * @param type
     */
    def addFiles(Long showId, String fileIds, Short type) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            showFileService.appendFiles(show, fileIds, type)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "true"
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }
}
