package mshow

import com.ybg.app.mshow.objectMarshaller.UserBaseMarshaller
import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date) {
            return it?.format("yyyy-MM-dd HH:mm:ss")
        }
        JSON.registerObjectMarshaller(new UserBaseMarshaller(), 9999)
    }
    def destroy = {
    }
}
