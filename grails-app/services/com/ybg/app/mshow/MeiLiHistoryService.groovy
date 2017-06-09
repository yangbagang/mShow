package com.ybg.app.mshow

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional(readOnly = true)
class MeiLiHistoryService {

    def dataSource

    def listSumMeili(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,sum(a.score) as scoreValue " +
                "from mei_li_history a left join user_base b on a.user_base_id = b.id " +
                "where a.create_time >= '${beginTime}' and a.create_time <= '${endTime}' " +
                "group by a.user_base_id order by scoreValue desc"
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumRenqi(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,sum(a.score) as scoreValue " +
                "from mei_li_history a left join user_base b on a.user_base_id = b.id " +
                "where b.create_time >= '${beginTime}' and b.create_time <= '${endTime}' " +
                "and a.type=1 group by a.user_base_id order by scoreValue desc "
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listSumHuoli(Integer pageNum, Integer pageSize, String beginTime, String endTime) {
        def sql = new Sql(dataSource)
        def query = "select a.user_base_id as user_id,b.avatar,b.nick_name as nickName,sum(a.score) as scoreValue " +
                "from mei_li_history a left join user_base b on a.user_base_id = b.id " +
                "where b.create_time >= '${beginTime}' and b.create_time <= '${endTime}' " +
                "and a.type=2 group by a.user_base_id order by scoreValue desc "
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }

    def listMiAi(Integer pageNum, Integer pageSize, String beginTime, String endTime, Long userId) {
        def sql = new Sql(dataSource)
        def query = "select a.from_user_id as user_id,b.avatar,b.nick_name as nickName,sum(a.score) as scoreValue " +
                "from mei_li_history a left join user_base b on a.from_user_id = b.id " +
                "where b.create_time >= '${beginTime}' and b.create_time <= '${endTime}' " +
                "and a.user_base_id=${userId} group by a.from_user_id order by scoreValue desc "
        sql.rows(query, (pageNum - 1) * pageSize, pageSize)
    }
}
