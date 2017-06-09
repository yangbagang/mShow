package com.ybg.app.mshow

class ShowFile {

    static belongsTo = [show: RuiShow]

    static constraints = {
    }

    String file//文件
    Short type//类型1图片2视频
    Short flag = 1 as Short//是否可用 1可用 0己删除
}
