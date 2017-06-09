package com.ybg.app.mshow.objectMarshaller

import com.ybg.app.mshow.UserBase
import com.ybg.app.utils.DateUtil
import grails.converters.JSON
import org.grails.web.converters.exceptions.ConverterException
import org.grails.web.converters.marshaller.ObjectMarshaller
import org.grails.web.json.JSONWriter

/**
 * Created by yangbagang on 2017/2/15.
 */
class UserBaseMarshaller implements ObjectMarshaller<JSON> {

    @Override
    boolean supports(Object object) {
        return object instanceof UserBase
    }

    @Override
    void marshalObject(Object object, JSON converter) throws ConverterException {
        JSONWriter writer = converter.getWriter()
        writer.object()
        writer.key('id').value(object.id)
                .key("ymCode").value(object.ymCode)
                .key('ymUser').value(object.ymUser)
                .key('nickName').value(object.nickName)
                .key("avatar").value(object.avatar)
                .key('avatarBG').value(object.avatarBG)
                .key('mobile').value(object.mobile)
                .key("flag").value(object.flag)
                .key('ymMemo').value(object.ymMemo)
                .key('createTime').value(DateUtil.getTimeString(object.createTime))
        writer.endObject()
    }

}
