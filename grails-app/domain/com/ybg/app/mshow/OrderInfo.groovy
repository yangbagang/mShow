package com.ybg.app.mshow

class OrderInfo {

    static belongsTo = [userBase: UserBase]

    static constraints = {
        orderNo unique: true
        isCancel nullable: true
        payStatus nullable: true
        orderWay nullable: true
        payWay nullable: true
        transNo nullable: true
        confirmTime nullable: true
    }

    String orderNo
    /** 作废标识 */
    Short isCancel = 0 as Short//0:未取消 1:手动取消 2:超时取消
    /** 付款状态  */
    Short payStatus = 0 as Short //0:未付款 1:已付款
    /** 订单渠道 */
    Short orderWay = 3 as Short  //0:线下 1:微信 2:WEB 3:APP
    /** 支付方式 */
    Short payWay   //0:银联 1:支付宝 2:微信支付 3:在线账户

    String transNo = ""
    RuiCard ruiCard
    Float orderMoney = 0f
    Date createTime = new Date()
    Date confirmTime//支付时间
}
