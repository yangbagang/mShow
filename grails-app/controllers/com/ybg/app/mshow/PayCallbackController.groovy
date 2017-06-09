package com.ybg.app.mshow

import com.pingplusplus.model.Charge
import com.pingplusplus.model.Webhooks
import com.ybg.app.utils.MsgPushHelper
import com.ybg.app.utils.PingPlusUtil
import grails.converters.JSON

import java.security.PublicKey

class PayCallbackController {

    def orderInfoService

    /**
     * PING++支付回调 - Webhooks payCallback/callback
     */
    def callback() {
        Map<String, byte[]> map = PingPlusUtil.getInfo(request)
        byte[] data = map.get("data")
        byte[] sign = map.get("sign")
        PublicKey publicKey = PingPlusUtil.getPubKey()
        boolean flag = PingPlusUtil.verifyData(data, sign, publicKey)
        // 不进行 签名校验
        flag = true
        if (flag) {
            Object obj = Webhooks.getObject(new String(data, "UTF-8"))
            if (obj instanceof Charge) {
                Charge charge = (Charge) obj
                /**
                 * 根据ping++异步通知，如果支付成功，修改订单状态
                 * */
                if (charge.paid) {//己经支付
                    String transaction_no = charge.transactionNo
                    // 支付成功-需要操作的步骤
                    TransactionInfo transactionInfo = TransactionInfo.findByChargeId(charge.id);
                    if(transactionInfo?.isSuccess == (1 as Short)){//如果已支付就不执行下面的操作
                        response.setStatus(200)
                        return
                    }
                    if (null != transactionInfo) {
                        /** 新增设置-存储支付账号 */
                        Map<String, Object> extraMap = charge.extra
                        if ("1" == transactionInfo.payType) {
                            transactionInfo.payAccount = extraMap.get("buyer_account").toString()// 支付宝支付账号
                        } else if ("2" == transactionInfo.payType) {
                            transactionInfo.payAccount = extraMap.get("open_id").toString()// 微信openid
                        }
                        //更新订单状态
                        OrderInfo orderInfo = OrderInfo.findByOrderNo(transactionInfo.orderNo)
                        orderInfoService.updateOrderStatus(orderInfo, transactionInfo, transaction_no)
                        //推送消息
                        def pushOrderVo = [:]
                        pushOrderVo.orderNo = orderInfo.orderNo
                        pushOrderVo.orderMoney = orderInfo.orderMoney
                        //个推
                        def userBase = orderInfo.userBase
                        def clientId = userBase.appToken
                        def result = [:]
                        result.type = MsgPushHelper.PAY_CALL_BACK
                        result.data = pushOrderVo
                        def content = result as JSON
                        MsgPushHelper msgPushHelper = new MsgPushHelper()
                        if(msgPushHelper.sendMsg(clientId, content.toString())){
                            println "回调推送 -----> 操作成功"
                        }else {
                            println "回调推送 -----> 操作失败"
                        }
                    } else {
                        println "----没有找到订单数据----" + charge.id
                    }
                } else {
                    println "----PING++ 交易失败----" + charge.id
                }
                response.setStatus(200);
            } else {
                response.setStatus(500);
            }
        } else {
            response.setStatus(500);
        }
        render ""
    }
}
