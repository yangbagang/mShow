package com.ybg.app.mshow

class RuiEvent {

    static belongsTo = [show: RuiShow, event: SystemEvent]

    static constraints = {

    }
}
