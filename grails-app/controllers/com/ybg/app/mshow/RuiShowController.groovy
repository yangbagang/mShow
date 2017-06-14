package com.ybg.app.mshow

import com.ybg.app.utils.UserUtil
import grails.converters.JSON

class RuiShowController {

    def ruiShowService

    def showViewHistoryService

    /**
     * 列出美秀。带分页。
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @param type 按哪种排序。1为最新，2为最热。
     * @return
     */
    def list(Integer pageNum, Integer pageSize, Integer type) {
        def map = [:]
        if (pageNum && pageSize && type) {
            def c = RuiShow.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                eq("flag", 1 as Short)
                if (type == 1 || type == 3) {
                    order("createTime", "desc")
                } else if (type == 2) {
                    order("viewNum", "desc")
                }
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 列出某个用户发的美秀。带分页。
     * @param userId
     * @param pageNum 显示第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    def listByUserId(Long userId, Integer pageNum, Integer pageSize) {
        def map = [:]
        if (pageNum && pageSize && userId) {
            def c = RuiShow.createCriteria()
            def result = c.list(max: pageSize, offset: (pageNum - 1) * pageSize) {
                userBase {
                    eq("id", userId)
                }
                eq("flag", 1 as Short)
                order("createTime", "desc")
            }
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = result
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 列出某个用户发的美秀数量。
     * @param userId
     * @return
     */
    def getShowNumByUserId(Long userId) {
        def map = [:]
        if (userId) {
            def user = UserBase.get(userId)
            def count = RuiShow.countByUserBase(user)
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = count
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = ""
        }
        render map as JSON
    }

    /**
     * 评价秀
     * @param token 用户token
     * @param showId 美秀ID
     * @param content 评价内容
     * @return
     */
    def ping(String token, Long showId, String content) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def num = ruiShowService.ping(ruiShow, userBase, content)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "${num}"
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 赞秀
     * @param token 用户token
     * @param showId 美秀ID
     * @return
     */
    def zan(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def zan = ShowZan.findByShowAndUserBase(ruiShow, userBase)
                if (zan) {
                    map.isSuccess = false
                    map.message = "己经赞过了，不能重复赞。"
                    map.errorCode = "3"
                    map.data = "false"
                } else {
                    def num = ruiShowService.zan(ruiShow, userBase)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = "${num}"
                }
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 分享秀
     * @param token 用户token
     * @param showId 美秀ID
     * @return
     */
    def share(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def ruiShow = RuiShow.get(showId)
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (ruiShow && ruiShow.flag == 1 as Short) {
                def num = ruiShowService.share(ruiShow, userBase)

                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "${num}"
            } else {
                map.isSuccess = false
                map.message = "美秀不存在，请检查。"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 显示美秀详细资料，包含相关附件，如附加的图片等。但不包括用户信息。
     * @param token
     * @param showId 美秀ID
     * @return
     */
    def detail(String token, Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            show.viewNum = show.viewNum + 1
            show.save flush: true

            if (token != "") {
                def userId = UserUtil.getUserId(token)
                def userBase = UserBase.get(userId)
                if (userBase != null) {
                    showViewHistoryService.create(show, userBase)
                }
            }

            def files = ShowFile.findAllByShowAndFlag(show, 1 as Short)
            show.files = files

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = show
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 发布一条美秀
     * @param token 用户token
     * @param thumbnail 缩略图
     * @param title 美秀描述
     * @param type 类型//1图片2视频。直播请使用createLive
     * @param price 价格
     * @return
     */
    def create(String token, String thumbnail, String title, Short type, Integer price) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            def show = ruiShowService.create(userBase, thumbnail, title, type, price)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = show
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 获取悦秀评论、点赞、分享数量，以及是否己经评论、点赞、是否己经分享、是否己经评论。
     * 以上数据保存在data中。pingNum评论数量，zanNum点赞数量，shareNum分享数量。
     * hasPing大于0表示己评论，等于0表示未评论；hasZan大于0表示己点赞，等于0表示未点赞；hasShare大于0表示己分享，0表示未分享。
     * @param token 用户token
     * @param showId 美秀ID
     */
    def getActionNum(String token, Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show && show.flag == 1 as Short) {
            show.viewNum = show.viewNum + 1
            show.save flush: true

            def num = ruiShowService.getActionNum(show, token)

            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = num
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 检查某条美秀状态。1正常，0己关闭。对于直播，1表示进行中，0表示己结束。
     * @param showId
     * @return
     */
    def checkStatus(Long showId) {
        def map = [:]
        def show = RuiShow.get(showId)
        if (show) {
            map.isSuccess = true
            map.message = ""
            map.errorCode = "0"
            map.data = "${show.flag}"
        } else {
            map.isSuccess = false
            map.message = "美秀不存在，请检查。"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 检查是否己经购买过
     * @param token
     * @param showId
     * @return
     */
    def checkPayStatus(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def show = RuiShow.get(showId)
                if (show) {
                    if (show.userBase == userBase || userBase.type > 0) {
                        map.isSuccess = true
                        map.message = "不需要付费或己经付过了"
                        map.errorCode = ""
                        map.data = "true"
                    } else if (show.price > 0 && !showViewHistoryService.hasView(userBase, show)) {
                        map.isSuccess = true
                        map.message = "需要付费才能看"
                        map.errorCode = "4"
                        map.data = "false"
                    } else {
                        map.isSuccess = true
                        map.message = "不需要付费或己经付过了"
                        map.errorCode = ""
                        map.data = "true"
                    }
                } else {
                    map.isSuccess = false
                    map.message = "美秀不存在"
                    map.errorCode = "3"
                    map.data = "false"
                }
            } else {
                map.isSuccess = false
                map.message = "用户不存在"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

    /**
     * 支付
     * @param token
     * @param showId
     * @return
     */
    def payForShow(String token, Long showId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def show = RuiShow.get(showId)
                if (show) {
                    if (show.price <= 0 || showViewHistoryService.hasView(userBase, show)) {
                        map.isSuccess = true
                        map.message = ""
                        map.errorCode = "0"
                        map.data = "true"
                    } else {
                        def userAccount = UserAccount.findByUserBase(userBase)
                        if (userAccount.restMoney >= show.price) {
                            ruiShowService.payShow(userAccount, show)
                            showViewHistoryService.create(show, userBase)

                            map.isSuccess = true
                            map.message = ""
                            map.errorCode = "0"
                            map.data = "true"
                        } else {
                            map.isSuccess = true
                            map.message = "余额不足"
                            map.errorCode = "4"
                            map.data = "false"
                        }
                    }
                } else {
                    map.isSuccess = false
                    map.message = "美秀不存在"
                    map.errorCode = "3"
                    map.data = "false"
                }
            } else {
                map.isSuccess = false
                map.message = "用户不存在"
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "登录凭证失效，请重新登录"
            map.errorCode = "1"
            map.data = "false"
        }

        render map as JSON
    }

}
