package com.ybg.app.utils

/**
 * Created by yangbagang on 2016/11/6.
 */
class MeiliConstant {

    //人气值
    static MEILI_RQ = 1 as Short
    static GET_ZAN = 2//作品获得点赞
    static GET_VIEW = 1//作品获得播放观看
    static GET_SHARE = 2//作品获得转发、分享
    static GET_PING = 1//作品收获评论
    static GET_MAX_GIFT = 200000//收获礼物最高20万

    //活力值
    static MEILI_HL = 2 as Short
    static POST_PHOTO_VIEW = 5//发布照片、视频
    static POST_LIVING = 8//进行直播
    static POST_SHARE = 2//对任何人作品进行转发、分享
    static POST_PING = 1//对任何人作品进行评论

    //亲善值
    static MEILI_QS = 3 as Short
    static COMPLETE_USER_INFO = 50//完善个人资料
    static POST_ZAN = 2//送出点赞
    static CREATE_HUODONG = 800//成功发起、参与线下会
    static POST_HUODONG = 8//线下会现场照片、视频发布
    static HUODONG_LIVE = 10//线下会现场、直播
    static FRIEND_GROUP = 800//亲友团、智团组建、互动
    static OFFLINE_FANS = 10//线下粉丝、密爱的互动、感恩
    static POST_MAX_GIFT = 200000//送出礼物
    static SYSTEM_MINUS = -10000//作品因不良情况被举报属实

}
