package com.ybg.app.mshow

import com.pingplusplus.model.Charge
import com.ybg.app.utils.PingPlusUtil
import com.ybg.app.utils.UserUtil
import grails.converters.JSON
import grails.transaction.Transactional

class OrderInfoController {

    def orderInfoService

    /**
     * 购买
     * @param machine
     * @param goodsJson
     * @param yhCode
     * @return
     */
    @Transactional
    def createOrder(String token, Long cardId) {
        def map = [:]
        if (UserUtil.checkToken(token)) {
            def userBase = UserBase.get(UserUtil.getUserId(token))
            if (userBase) {
                def ruiCard = RuiCard.get(cardId)
                if (ruiCard) {
                    def orderInfo = orderInfoService.createOrder(userBase, ruiCard)

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = orderInfo.orderNo
                } else {
                    map.isSuccess = false
                    map.message = "参数错误"
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
     * 查询某个订单是否己经成功支付
     * @param orderNo
     */
    def queryOrderIsPay(String orderNo) {
        def map = [:]
        def orderInfo = OrderInfo.findByOrderNo(orderNo)
        if (orderInfo) {
            if (orderInfo.payStatus == (1 as Short)) {//己经支付
                map.isSuccess = true
                map.message = ""
                map.errorCode = "0"
                map.data = "${orderInfo.payWay}"
            } else {
                //未支付,有可能是ping++没有回调webhook
                //此处进行主动查询,如果己经支付则更新状态并返回
                def transaction = TransactionInfo.findByOrderNo(orderNo)
                if (transaction) {
                    try {
                        //查询交易数据
                        Charge charge = PingPlusUtil.retrieve(transaction.chargeId)
                        if (charge.getPaid()) {
                            Map<String, String> extraMap = charge.getExtra()
                            if ("1" == transaction.payType) {
                                transaction.payAccount = extraMap.get("buyer_account")// 支付宝支付账号
                            } else if ("2" == transaction.payType) {
                                transaction.payAccount = extraMap.get("open_id")// 微信openid
                            }
                            //更新订单状态
                            orderInfoService.updateOrderStatus(orderInfo, transaction, charge.getTransactionNo())

                            map.isSuccess = true
                            map.message = ""
                            map.errorCode = "0"
                            map.data = "${orderInfo.payWay}"
                        } else {
                            map.isSuccess = false
                            map.message = ""
                            map.errorCode = "2"
                            map.data = "false"
                        }
                    } catch (Exception e) {
                        map.isSuccess = false
                        map.message = ""
                        map.errorCode = "2"
                        map.data = "false"
                        println "********queryOrderInfo********查询charge报错:" + e.getMessage()
                    }
                } else {
                    map.isSuccess = false
                    map.message = ""
                    map.errorCode = "2"
                    map.data = "false"
                }
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

    /**
     * 创建ping++订单,返回信息将用于生成二维码。
     * @param machineId
     * @param orderNo
     * @param payType
     */
    @Transactional
    def createPingPlusCharge(String orderNo, String payType) {
        def map = [:]
        if (orderNo && payType) {
            try {
                def orderInfo = OrderInfo.findByOrderNo(orderNo)
                orderInfo.payWay = Short.valueOf(payType)
                map = PingPlusUtil.pay(request.getRemoteAddr(), orderInfo.orderMoney, payType, orderNo,
                        "购买商品-${orderNo}", "APP购买商品", orderNo)
                Charge charge = (Charge) map.get("charge")
                //记录chargeId,以及订单号,金额等信息-到支付记录表
                if (charge) {
                    def transactionInfo = TransactionInfo.findByOrderNo(orderNo)
                    if (!transactionInfo) {
                        transactionInfo = new TransactionInfo()
                        transactionInfo.orderNo = orderNo
                    }
                    transactionInfo.chargeId = charge.getId()
                    transactionInfo.orderMoney = orderInfo.orderMoney
                    transactionInfo.payType = payType
                    transactionInfo.isSuccess = 0 as Short//是否支付成功 0：未支付；1：支付成功
                    transactionInfo.createTime = new Date()
                    transactionInfo.save flush: true

                    map.isSuccess = true
                    map.message = ""
                    map.errorCode = "0"
                    map.data = charge
                }
            } catch (Exception e) {
                e.printStackTrace()
                map.isSuccess = false
                map.message = e.getMessage()
                map.errorCode = "2"
                map.data = "false"
            }
        } else {
            map.isSuccess = false
            map.message = "参数不能为空"
            map.errorCode = "1"
            map.data = "false"
        }
        render map as JSON
    }

}
