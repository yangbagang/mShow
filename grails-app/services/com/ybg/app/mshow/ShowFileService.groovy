package com.ybg.app.mshow

import grails.transaction.Transactional

@Transactional
class ShowFileService {

    def appendFiles(RuiShow show, String fileIds, Short type) {
        if (show && fileIds) {
            String[] ids = fileIds.split(",")
            for (id in ids) {
                def showFile = new ShowFile()
                showFile.file = id
                showFile.type = type
                showFile.flag = 1 as Short
                showFile.show = show

                showFile.save flush: true
            }
        }
    }
}
