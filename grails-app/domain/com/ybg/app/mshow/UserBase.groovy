package com.ybg.app.mshow

class UserBase {

    static constraints = {
        ymCode unique: true
        ymUser nullable: true
        password nullable: true
        nickName nullable: true
        avatar nullable: true
        avatarBG nullable: true
        mobile nullable: true
        flag nullable: true
        ymMemo nullable: true
        tjUser nullable: true
        appToken nullable: true
    }

    Long ymCode = 0//悦美号，唯一，非空，自动生成，不可修改。
    String ymUser = ""//悦美账号，通常是手机号
    String password = ""//密码
    String nickName = "未填写"//呢称，可空，非唯一。
    String avatar = ""//头像
    String avatarBG = ""//头像背景
    String mobile = ""//手机号
    Short flag = 1 as Short//是否有效1有效0无效
    String ymMemo = ""//宣言
    UserBase tjUser//推荐
    Date createTime = new Date()
    String appToken = ""

}
