package com.ybg.app.mshow

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional(readOnly = true)
class ShowBangService {

    def dataSource

    def listSumShow(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,count(a.id) as scoreValue " +
                "from rui_show a left join user_base b on a.user_base_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' " +
                "group by a.user_base_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumView(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,sum(a.view_num) as scoreValue " +
                "from rui_show a left join user_base b on a.user_base_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' " +
                "group by a.user_base_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumIncome(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,sum(a.rui_money) as scoreValue " +
                "from user_account_history a left join user_base b on a.user_base_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' and a.rui_money > 0 " +
                "group by a.user_base_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumOutcome(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,abs(sum(a.rui_money)) as scoreValue " +
                "from user_account_history a left join user_base b on a.user_base_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' and a.rui_money < 0 " +
                "group by a.user_base_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumMoney(Long userId, Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.from_user_id as user_id,b.avatar,b.nick_name as nickName,abs(sum(a.rui_money)) as scoreValue " +
                "from user_account_history a left join user_base b on a.from_user_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' and a.user_base_id = ${userId} " +
                "group by a.from_user_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

}
