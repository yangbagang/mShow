package com.ybg.app.mshow

class RuiShow {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        thumbnail nullable: true
        title nullable: true
        ruiBar nullable: true
    }

    String thumbnail = ""//缩略图
    String title = ""//说明
    Date createTime = new Date()//发布时间
    Date updateTime = new Date()//结束时间
    Short type = 1 as Short//1图片2视频3直播
    Integer viewNum = 0//查看次数
    Integer pingNum = 0//评论次数
    Integer zanNum = 0//赞次数
    Integer shareNum = 0//分享次数
    RuiBar ruiBar
    Short flag = 1 as Short//标志，1可用，0不可用
    Integer price = 0//需要付多少钱才可以看。免费对应的价格为0。

    transient List<ShowFile> files
    transient Integer fileNum
    Integer getFileNum() {
        ShowFile.countByShow(this)
    }
}
