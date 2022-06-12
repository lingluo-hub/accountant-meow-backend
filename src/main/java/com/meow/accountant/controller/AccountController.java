package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.util.MapUtils;
import com.meow.accountant.entity.Account;
import com.meow.accountant.entity.BarCharItem;
import com.meow.accountant.entity.ChartItem;
import com.meow.accountant.entity.response.ResponseResult;
import com.meow.accountant.service.AccountServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 凌洛
 * @Description <p>Controller层</p>
 * <p>kind：int, 记账类型（支出=0、收入=1）</p>
 * <p>userid: 用户id唯一标识，区别记录</p>
 */

@RestController
@RequestMapping("/accountant-meow")
public class AccountController {

    Logger logger = LogManager.getLogger("执行记账操作");

    private final AccountServiceImpl accountService;
    private final MapSearcher mapSearcher;

    private static final String DO_NOT_EXIST = "Record does not exist!";

    public AccountController(AccountServiceImpl accountService, MapSearcher mapSearcher) {
        this.accountService = accountService;
        this.mapSearcher = mapSearcher;
    }

    /**
     * 全局检索，使用 BeanSearch 框架
     *
     * @param request http请求
     * @return List
     */
    @GetMapping("/index")
    public SearchResult<Map<String, Object>> index(HttpServletRequest request) {
        return mapSearcher.search(Account.class, MapUtils.flat(request.getParameterMap()), new String[]{"money"}); //money字段统计
    }

    /**
     * 向记账表当中插入一条元素
     *
     * @param typename 类型名
     * @param sImageId 被选中图片id
     * @param beizhu   备注
     * @param money    记录金额
     * @param time     时间
     * @param year     记账年份
     * @param month    记账月份
     * @param day      记账日
     * @param kind     类型
     * @param userid   用户id
     */
    @PostMapping("/insertAccount")
    public ResponseResult<List<Account>> insertAccount(@RequestParam String typename, @RequestParam int sImageId, @RequestParam String beizhu,
                                                       @RequestParam float money, @RequestParam String time,
                                                       @RequestParam int year, @RequestParam int month, @RequestParam int day,
                                                       @RequestParam int kind, @RequestParam String userid) {
        accountService.insertAccount(typename, sImageId, beizhu, money, time, year, month, day, kind, userid);
        logger.info("HTTP:POST insertAccount: {}", userid);
        return ResponseResult.success();
    }

    /**
     * 获取记账表当中某一天的所有支出或者收入情况
     *
     * @param year   记账年
     * @param month  记账月
     * @param day    记账日
     * @param userid 用户id
     * @return AccountList
     */
    @RequestMapping("/getAccountByDate")
    public ResponseResult<List<Account>> getAccountByDate(@RequestParam int year, @RequestParam int month, @RequestParam int day, @RequestParam String userid) {
        logger.info("HTTP:GET getAccountByDate:{}", userid);
        return ResponseResult.success(accountService.getAccountByDate(year, month, day, userid));
    }

    /**
     * 获取记账表当中某一月的所有支出或者收入情况
     *
     * @param year   记账年份
     * @param month  记账月份
     * @param userid 用户id
     * @return AccountList
     */
    @RequestMapping("/getAccountByMonth")
    public ResponseResult<List<Account>> getAccountByMonth(@RequestParam int year, @RequestParam int month, @RequestParam String userid) {
        logger.info("HTTP:GET getAccountByMonth:{}", userid);
        return ResponseResult.success(accountService.getAccountByMonth(year, month, userid));
    }

    //获取某一天的支出或者收入的总金额   kind：支出==0    收入===1

    /**
     * 获取某一天的支出或者收入的总金额
     *
     * @param year   记账年
     * @param month  记账月
     * @param day    记账日
     * @param kind   记账类型
     * @param userid 用户id
     * @return Float
     */
    @RequestMapping("/getSumMoneyOneDay")
    public ResponseResult<Float> getSumMoneyOneDay(@RequestParam int year, @RequestParam int month, @RequestParam int day,
                                                   @RequestParam int kind, @RequestParam String userid) {
        Float result = accountService.getSumMoneyOneDay(year, month, day, kind, userid);
        logger.info("HTTP:GET getSumMoneyOneDay:{}", userid);
        if (result == null) {
            return ResponseResult.fail("");
        }
        return ResponseResult.success(result);
    }

    /**
     * 获取某一月的支出或者收入的总金额
     *
     * @param year   记账年
     * @param month  记账月
     * @param kind   记账类型
     * @param userid 用户id
     * @return Float
     */
    @RequestMapping("/getSumMoneyOneMonth")
    public ResponseResult<Float> getSumMoneyOneMonth(@RequestParam int year, @RequestParam int month, @RequestParam int kind, @RequestParam String userid) {
        Float result = accountService.getSumMoneyOneMonth(year, month, kind, userid);
        if (result == null) {
            logger.info("HTTP:GET getSumMoneyOneMonth:{}", userid);
            return ResponseResult.fail(DO_NOT_EXIST);
        }
        return ResponseResult.success(result);
    }

    /**
     * 统计某月份支出或者收入情况有多少条
     *
     * @param year   记账年
     * @param month  记账月
     * @param kind   记账日
     * @param userid 用户id
     * @return int
     */
    @RequestMapping("/getCountItemOneMonth")
    public ResponseResult<Integer> getCountItemOneMonth(@RequestParam int year, @RequestParam int month, @RequestParam int kind, @RequestParam String userid) {
        logger.info("HTTP:GET getCountItemOneMonth:{}", userid);
        return ResponseResult.success(accountService.getCountItemOneMonth(year, month, kind, userid));
    }

    /**
     * 获取某一年的支出或者收入的总金额
     *
     * @param year   记账年
     * @param kind   记账月
     * @param userid 用户id
     * @return float
     */
    @RequestMapping("/getSumMoneyOneYear")
    public ResponseResult<Float> getSumMoneyOneYear(@RequestParam int year, @RequestParam int kind, @RequestParam String userid) {
        logger.info("HTTP:GET getSumMoneyOneYear:{}", userid);
        return ResponseResult.success(accountService.getSumMoneyOneYear(year, kind, userid));
    }

    /**
     * 根据传入的id，删除accounttb表当中的一条数据
     *
     * @param id     数据库表记录id
     * @param userid 用户id
     */
    @DeleteMapping("/deleteItemFromAccounttbById")
    public ResponseResult<String> deleteItemFromAccounttbById(@RequestParam int id, @RequestParam String userid) {
        accountService.deleteItemFromAccounttbById(id, userid);
        logger.warn("HTTP:DELETE deleteItemFromAccounttbById:{}", userid);
        return ResponseResult.success("Delete record successfully");
    }

    //

    /**
     * 根据传入的id，修改accounttb中的一条数据
     *
     * @param id       数据库记录id
     * @param typename 类型名
     * @param sImageId 被选中的图片id
     * @param beizhu   备注
     * @param money    记账金额
     * @param time     时间
     * @param year     记账年
     * @param month    记账月
     * @param day      记账日
     * @param kind     记账类型
     * @param userid   用户id
     */
    @PutMapping("/updateItemFromAccounttbById")
    public ResponseResult<List<Account>> updateItemFromAccounttbById(@RequestParam int id, @RequestParam String typename, @RequestParam int sImageId,
                                                                     @RequestParam String beizhu, @RequestParam float money,
                                                                     @RequestParam String time, @RequestParam int year,
                                                                     @RequestParam int month, @RequestParam int day,
                                                                     @RequestParam int kind, @RequestParam String userid) {
        accountService.updateItemFromAccounttbById(id, typename, sImageId, beizhu, money, time, year, month, day, kind, userid);
        logger.info("HTTP:PUT updateItemFromAccounttbById:{}", userid);
        return ResponseResult.success();
    }

    /**
     * 查询记账的表当中有几个年份信息
     *
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getYearListFromAccounttb")
    public ResponseResult<List<Integer>> getYearListFromAccounttb(@RequestParam String userid) {
        logger.info("HTTP:GET getYearListFromAccounttb:{}", userid);
        return ResponseResult.success(accountService.getYearListFromAccounttb(userid));
    }

    /**
     * 查询指定年份和月份的收入或者支出每一种类型的总钱数
     *
     * @param year   记账年
     * @param month  记账月
     * @param kind   记账类型
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getChartListFromAccounttb")
    public ResponseResult<List<ChartItem>> getChartListFromAccounttb(@RequestParam int year, @RequestParam int month, @RequestParam int kind, @RequestParam String userid) {
        List<ChartItem> result = accountService.getChartListFromAccounttb(year, month, kind, userid);
        logger.info("HTTP:GET getChartListFromAccounttb:{}", userid);
        return ResponseResult.success(result);
    }

    /**
     * 某月单日最大支出/收入额
     *
     * @param year   记账年
     * @param month  记账月
     * @param kind   记账类型
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getMaxMoneyOneDayInMonth")
    public ResponseResult<List<Float>> getMaxMoneyOneDayInMonth(@RequestParam int year, @RequestParam int month, @RequestParam int kind, @RequestParam String userid) {
        logger.info("HTTP:GET getMaxMoneyOneDayInMonth:{}", userid);
        return ResponseResult.success(accountService.getMaxMoneyOneDayInMonth(year, month, kind, userid));
    }

    /**
     * 根据指定月份每一日收入或者支出的总钱数的集合
     *
     * @param year   记账年
     * @param month  记账月
     * @param kind   记账类型
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getSumMoneyOneDayInMonth")
    public ResponseResult<List<BarCharItem>> getSumMoneyOneDayInMonth(@RequestParam int year, @RequestParam int month, @RequestParam int kind, @RequestParam String userid) {
        logger.info("HTTP:GET getSumMoneyOneDayInMonth:{}", userid);
        return ResponseResult.success(accountService.getSumMoneyOneDayInMonth(year, month, kind, userid));
    }

    /**
     * 根据备注搜索收入/支出的情况列表
     *
     * @param beizhu 备注
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getAccountListByRemarkFromAccounttb")
    public ResponseResult<List<Account>> getAccountListByRemarkFromAccounttb(@RequestParam String beizhu, @RequestParam String userid) {
        logger.info("HTTP:GET getAccountListByRemarkFromAccounttb:{}", userid);
        return ResponseResult.success(accountService.getAccountListByRemarkFromAccounttb(beizhu, userid));
    }

    /**
     * 根据类型搜索收入/支出的情况列表
     *
     * @param type   类型
     * @param userid 用户id
     * @return List
     */
    @GetMapping("/getAccountListByTypeFromAccounttb")
    public ResponseResult<List<Account>> getAccountListByTypeFromAccounttb(@RequestParam String type, @RequestParam String userid) {
        logger.info("HTTP:GET getAccountListByTypeFromAccounttb:{}", userid);
        return ResponseResult.success(accountService.getAccountListByTypeFromAccounttb(type, userid));
    }

    /**
     * 获取预算
     *
     * @param userid 用户id
     * @return Float
     */
    @GetMapping("/getBudget")
    public ResponseResult<Float> getBudget(@RequestParam String userid) {
        Float result = accountService.getBudget(userid);
        logger.info("HTTP:GET getBudget:{}", userid);
        if (result == null) {
            return ResponseResult.fail(DO_NOT_EXIST);
        }
        return ResponseResult.success(result);
    }

    /**
     * 插入预算
     *
     * @param userid 用户id
     * @param budget 预算记录
     */
    @PostMapping("/insertBudget")
    public ResponseResult<Float> insertBudget(@RequestParam String userid, @RequestParam float budget) {
        logger.info("HTTP:POST insertBudget:{}", userid);
        accountService.insertBudget(userid, budget);
        return ResponseResult.success();
    }
}
