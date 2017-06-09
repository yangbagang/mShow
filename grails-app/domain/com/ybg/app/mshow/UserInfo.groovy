package com.ybg.app.mshow

import com.ybg.app.utils.DateUtil

class UserInfo {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        birthday nullable: true
        sex nullable: true
        position nullable: true
        bodyHigh nullable: true
        bodyWeight nullable: true
        cupSize nullable: true
        bust nullable: true
        waist nullable: true
        hips nullable: true
        province nullable: true
        city nullable: true
    }

    Date birthday = DateUtil.getDefaultBirthday()//生日
    Short sex = 1 as Short//性别1男0女
    String position = "未选择"//职位
    Integer bodyHigh = 0//身高，单位cm
    Integer bodyWeight = 0//体重，单位kg
    String cupSize = ""//罩杯
    Integer bust = 0//胸围bust 单位cm
    Integer waist = 0//腰围waist 单位cm
    Integer hips = 0//臀围hips 单位cm
    String province = ""//省份
    String city = ""//市
}
