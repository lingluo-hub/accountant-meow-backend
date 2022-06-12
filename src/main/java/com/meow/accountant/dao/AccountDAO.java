package com.meow.accountant.dao;

import com.meow.accountant.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihangqi
 */
@Mapper
@Repository
public interface AccountDAO {

    List<Account> getAccountByUserid(String userid);

    List<Account> getAccountByDate(int year, int month, int day, String userid);

    List<Account> getAccountByMonth(int year, int month, String userid);

    Float getSumMoneyOneDay(int year, int month, int day, int kind, String userid);

    Float getSumMoneyOneMonth(int year, int month, int kind, String userid);

    Integer getCountItemOneMonth(int year, int month, int kind, String userid);

    Float getSumMoneyOneYear(int year, int kind, String userid);

    void deleteItemFromAccounttbById(int id, String userid);

    void insertAccount(String typename, int sImageId, String beizhu, float money, String time, int year, int month, int day, int kind, String userid);

    void updateItemFromAccounttbById(int id, String typename, int sImageId, String beizhu, float money,
                                     String time, int year, int month, int day, int kind, String userid);

    List<Integer> getYearListFromAccounttb(String userid);

    List<ResultFromAccounttb> getResultFromAccounttb(int year, int month, int kind, String userid);

    List<ChartItem> getChartListFromAccounttb(int year, int month, int kind, String userid);

    List<Float> getMaxMoneyOneDayInMonth(int year, int month, int kind, String userid);

    List<ResultSumMoneyOneDayInMonth> getResultSumMoneyOneDayInMonth(int year, int month, int kind, String userid);

    List<BarCharItem> getSumMoneyOneDayInMonth(int year, int month, int kind, String userid);

    List<Account> getAccountListByRemarkFromAccounttb(String beizhu, String userid);

    List<Account> getAccountListByTypeFromAccounttb(String type, String userid);

    Float getBudget(String userid);

    void insertBudget(String userid, float budget);
}
