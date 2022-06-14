package com.meow.accountant;

import com.ejlchina.searcher.SqlWrapper;
import com.ejlchina.searcher.param.Paging;
import com.meow.accountant.config.PostgreSqlDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PostgreSqlDialectTest {

    PostgreSqlDialect postgreSqlDialect = new PostgreSqlDialect();

    @Test
    void toUpperCase() {
        StringBuilder builder = new StringBuilder("select");
        String dbField = "name";
        postgreSqlDialect.toUpperCase(builder, dbField);
        Assertions.assertEquals("selectupper(name)", builder.toString());
    }

    @Test
    void forPaginate() {
        String fieldSelectSql = "select id,money,userid";
        String fromWhereSql = "from accounttb";
        Paging paging = new Paging(2, 10);
        SqlWrapper<Object> resultsql = postgreSqlDialect.forPaginate(fieldSelectSql, fromWhereSql, paging);
        SqlWrapper<Object> resultsqlNullPaging = postgreSqlDialect.forPaginate(fieldSelectSql, fromWhereSql, null);
        Assertions.assertEquals("select id,money,useridfrom accounttb offset ? limit ?", resultsql.getSql());
        Assertions.assertEquals("[10, 2]", resultsql.getParas().toString());
        Assertions.assertEquals("select id,money,useridfrom accounttb",resultsqlNullPaging.getSql());
        Assertions.assertEquals("[]", resultsqlNullPaging.getParas().toString());
    }
}