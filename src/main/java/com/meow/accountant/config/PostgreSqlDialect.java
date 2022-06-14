package com.meow.accountant.config;

import com.ejlchina.searcher.SqlWrapper;
import com.ejlchina.searcher.dialect.Dialect;
import com.ejlchina.searcher.param.Paging;

public class PostgreSqlDialect implements Dialect {

    @Override
    public void toUpperCase(StringBuilder builder, String dbField) {
        builder.append("upper").append("(").append(dbField).append(")");
    }

    @Override
    public SqlWrapper<Object> forPaginate(String fieldSelectSql, String fromWhereSql, Paging paging) {
        SqlWrapper<Object> wrapper = new SqlWrapper<>();
        StringBuilder ret = new StringBuilder();
        ret.append(fieldSelectSql).append(fromWhereSql);
        if (paging != null) {
            ret.append(" offset ? limit ?");
            wrapper.addPara(paging.getOffset());
            wrapper.addPara(paging.getSize());
        }
        wrapper.setSql(ret.toString());
        return wrapper;
    }

}