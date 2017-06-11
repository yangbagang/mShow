package com.ybg.app.mshow

import com.ybg.app.utils.OrderUtil
import grails.transaction.Transactional

@Transactional
class OrderInfoService {

    def createOrder(UserBase userBase, RuiCard ruiCard) {
        def orderNo = OrderUtil.createOrderNo()
        //orderInfo
        def orderInfo = new OrderInfo()
        orderInfo.userBase = userBase
        orderInfo.orderNo = orderNo
        orderInfo.ruiCard = ruiCard
        orderInfo.orderMoney = ruiCard.realPrice
        orderInfo.save flush: true

        orderInfo
    }

    def updateOrderStatus(OrderInfo orderInfo, TransactionInfo transactionInfo, String transaction_no) {
        //update order
        orderInfo.transNo = transaction_no
        orderInfo.payStatus = 1 as Short
        orderInfo.confirmTime = new Date()
        orderInfo.payWay = Short.valueOf(transactionInfo.payType)
        transactionInfo.isSuccess = 1 as Short
        transactionInfo.updateTime = new Date()
        transactionInfo.save flush: true
        orderInfo.save flush: true

        //update 秀币
        def userBase = orderInfo.userBase
        def userAccount = UserAccount.findByUserBase(userBase)
        userAccount.totalMoney += orderInfo.ruiCard.ruiMoney
        userAccount.restMoney += orderInfo.ruiCard.ruiMoney
        userAccount.save flush: true

        def userAccountHistory = new UserAccountHistory()
        userAccountHistory.userBase = userBase
        userAccountHistory.ruiMoney = orderInfo.ruiCard.ruiMoney
        userAccountHistory.reason = "充值"
        userAccountHistory.reasonType = 1
        userAccountHistory.save flush: true
    }

}
