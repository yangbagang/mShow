package com.ybg.app.mshow

class SystemAchievement {

    static constraints = {
        name unique: true
        px nullable: true
        catalog nullable: true
        image nullable: true
    }

    String name//成就名称
    Integer px = 1//排序
    String catalog = ""//分类
    String image = ""//图像

}
