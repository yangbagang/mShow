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
        platform nullable: true
        platformId nullable: true
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
    String platform = ""//平台
    String platformId = ""//平台ID
    Date createTime = new Date()
    String appToken = ""
    transient Integer ml

    def copyInstance() {
        def instance = new UserBase()
        instance.id = this.id
        instance.ymCode = this.ymCode
        instance.ymUser = this.ymUser
        instance.nickName = this.nickName
        instance.avatar = this.avatar
        instance.avatarBG = this.avatarBG
        instance.mobile = this.mobile
        instance.flag = this.flag
        instance.ymMemo = this.ymMemo
        instance.platform = this.platform
        instance.platformId = this.platformId
        instance.createTime = this.createTime
        instance.appToken = this.appToken
        instance
    }
}
