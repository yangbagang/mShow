package com.ybg.app.mshow

import grails.transaction.Transactional

@Transactional
class RuiEventService {

    def appendEvents(RuiShow show, String eventIds) {
        if (show && eventIds) {
            String[] ids = eventIds.split(",")
            for (id in ids) {
                def event = SystemEvent.get(Long.valueOf(id))
                def showEvent = new RuiEvent()
                showEvent.show = show
                showEvent.event = event
                showEvent.save flush: true
            }
        }
    }
}
