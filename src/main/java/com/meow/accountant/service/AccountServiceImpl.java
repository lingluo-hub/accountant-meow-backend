package com.meow.accountant.service;

import com.meow.accountant.dao.AccountDAO;
import com.meow.accountant.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 凌洛
 */
@Service
public class AccountServiceImpl implements AccountDAO {


    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<Account> getAccountByUserid(String userid) {
        return accountDAO.getAccountByUserid(userid);
    }

    @Override
    public List<Account> getAccountByDate(int year, int month, int day, String userid) {
        return accountDAO.getAccountByDate(year, month, day, userid);
    }

    @Override
    public List<Account> getAccountByMonth(int year, int month, String userid) {
        return accountDAO.getAccountByMonth(year, month, userid);
    }

    @Override
    public Float getSumMoneyOneDay(int year, int month, int day, int kind, String userid) {
        return accountDAO.getSumMoneyOneDay(year, month, day, kind, userid);
    }

    @Override
    public Float getSumMoneyOneMonth(int year, int month, int kind, String userid) {
        return accountDAO.getSumMoneyOneMonth(year, month, kind, userid);
    }

    public Integer getCountItemOneMonth(int year, int month, int kind, String userid) {
        return accountDAO.getCountItemOneMonth(year, month, kind, userid);
    }

    @Override
    public Float getSumMoneyOneYear(int year, int kind, String userid) {
        return accountDAO.getSumMoneyOneYear(year, kind, userid);
    }

    @Override
    public void deleteItemFromAccounttbById(int id, String userid) {
        accountDAO.deleteItemFromAccounttbById(id, userid);
    }

    @Override
    public void insertAccount(String typename, int sImageId, String beizhu, float money, String time, int year, int month, int day, int kind, String userid) {
        accountDAO.insertAccount(typename, sImageId, beizhu, money, time, year, month, day, kind, userid);
    }

    @Override
    public void updateItemFromAccounttbById(int id, String typename, int sImageId, String beizhu, float money,
                                            String time, int year, int month, int day, int kind, String userid) {
        accountDAO.updateItemFromAccounttbById(id, typename, sImageId, beizhu, money, time, year, month, day, kind, userid);
    }

    @Override
    public List<Integer> getYearListFromAccounttb(String userid) {
        return new ArrayList<>(accountDAO.getYearListFromAccounttb(userid));
    }

    @Override
    public void deleteAllAccount(String userid) {
        accountDAO.deleteAllAccount(userid);
    }

    /*
     * "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                "order by total desc"
     * 获取上述SQL结果并存入ResultFromAccounttb数据实体中
     */
    @Override
    public List<ResultFromAccounttb> getResultFromAccounttb(int year, int month, int kind, String userid) {
        return accountDAO.getResultFromAccounttb(year, month, kind, userid);
    }

    @Override
    public List<ChartItem> getChartListFromAccounttb(int year, int month, int kind, String userid) {
        if (getSumMoneyOneMonth(year, month, kind, userid) == null) {
            return Collections.emptyList();
        }
        float sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind, userid);
        ArrayList<ChartItem> recoList = new ArrayList<>();
        for (int i = 0; i < getResultFromAccounttb(year, month, kind, userid).size(); i++) {
            ChartItem chartItem = new ChartItem();
            chartItem.setSImageId(getResultFromAccounttb(year, month, kind, userid).get(i).getSImageId());
            chartItem.setTypename(getResultFromAccounttb(year, month, kind, userid).get(i).getTypename());
            chartItem.setTotal(getResultFromAccounttb(year, month, kind, userid).get(i).getTotal());
            float ratio = chartItem.getTotal() / sumMoneyOneMonth;
            chartItem.setRatio(ratio);
            recoList.add(chartItem);
        }
        return recoList;
    }

    @Override
    public List<Float> getMaxMoneyOneDayInMonth(int year, int month, int kind, String userid) {
        return new ArrayList<>(accountDAO.getMaxMoneyOneDayInMonth(year, month, kind, userid));
    }

    /*
     * select day,sum(money) from accounttb where year=? and month=? and kind=? group by day
     * 获取上述SQL结果并存入ResultSumMoneyOneDayInMonth数据实体中
     */
    @Override
    public List<ResultSumMoneyOneDayInMonth> getResultSumMoneyOneDayInMonth(int year, int month, int kind, String userid) {
        return accountDAO.getResultSumMoneyOneDayInMonth(year, month, kind, userid);
    }

    @Override
    public List<BarCharItem> getSumMoneyOneDayInMonth(int year, int month, int kind, String userid) {
        ArrayList<BarCharItem> recoList = new ArrayList<>();
        for (int i = 0; i < getResultSumMoneyOneDayInMonth(year, month, kind, userid).size(); i++) {
            BarCharItem barCharItem = new BarCharItem();
            barCharItem.setYear(year);
            barCharItem.setMonth(month);
            barCharItem.setDay(getResultSumMoneyOneDayInMonth(year, month, kind, userid).get(i).getDay());
            float summoney = getResultSumMoneyOneDayInMonth(year, month, kind, userid).get(i).getSummoney();
            barCharItem.setSummoney(summoney);
            recoList.add(barCharItem);
        }
        return recoList;
    }

    @Override
    public List<Account> getAccountListByRemarkFromAccounttb(String beizhu, String userid) {
        beizhu = "%" + beizhu + "%";
        return accountDAO.getAccountListByRemarkFromAccounttb(beizhu, userid);
    }

    @Override
    public List<Account> getAccountListByTypeFromAccounttb(String type, String userid) {
        return accountDAO.getAccountListByTypeFromAccounttb(type, userid);
    }

    @Override
    public Float getBudget(String userid) {
        return accountDAO.getBudget(userid);
    }

    @Override
    public void insertBudget(String userid, float budget) {
        accountDAO.insertBudget(userid, budget);
    }

}
