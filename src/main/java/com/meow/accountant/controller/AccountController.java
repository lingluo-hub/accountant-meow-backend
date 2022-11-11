package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.util.MapUtils;
import com.meow.accountant.entity.Accounttb;
import com.meow.accountant.entity.response.ResponseResult;
import com.meow.accountant.service.impl.AccounttbServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 凌洛
 * @Description <p>记录 controller 层</p>
 * <p>kind：int, 记账类型（支出=0、收入=1）</p>
 * <p>userid: 用户id唯一标识，区别记录</p>
 */
@RestController
@RequestMapping("/api/v2/")
public class AccountController {

    private final MapSearcher mapSearcher;
    private final AccounttbServiceImpl accounttbService;

    public AccountController(MapSearcher mapSearcher, AccounttbServiceImpl accounttbService) {
        this.mapSearcher = mapSearcher;
        this.accounttbService = accounttbService;
    }

    Logger logger = LogManager.getLogger("执行记账操作");

    /**
     * 全局检索，使用 BeanSearch 框架
     *
     * @return List
     */
    @GetMapping("/index-account")
    @ApiOperation("全局搜索")
    public SearchResult<Map<String, Object>> index(Integer year, Integer month, Integer day, String typename,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size, Integer kind,
                                                   @RequestParam(value = "money-0", required = false) Double money_0,
                                                   @RequestParam(value = "money-1", required = false) Double money_1,
                                                   @RequestParam(value = "beizhu-op", required = false) String beizhu,
                                                   @RequestParam(value = "userid") String userid) {
        Map<String, Object> params = MapUtils.builder()
                .field(Accounttb::getUserid, userid)
                .field(Accounttb::getYear, year)
                .field(Accounttb::getMonth, month)
                .field(Accounttb::getDay, day)
                .field(Accounttb::getKind, kind)
                .field(Accounttb::getMoney, money_0, money_1)
                .field(Accounttb::getBeizhu, beizhu).op(beizhu)
                .orderBy(typename)
                .page(page, size)
                .build();
        logger.info("全局查询记录: " + userid);
        return mapSearcher.search(Accounttb.class, params, new String[]{"money"});
    }

    @PostMapping("/account")
    @ApiOperation("插入/更新记录")
    public ResponseResult<String> account(Accounttb accounttb) {
        if (accounttbService.exists(accounttb.getId())) {
            accounttbService.updateAccount(accounttb);
            logger.info("更新记录: " + accounttb.getId());
            return ResponseResult.success("update succeed!");
        }
        accounttbService.addAccount(accounttb);
        logger.info("插入记录: " + accounttb.getUserid());
        return ResponseResult.success("insert succeed!");
    }

    @DeleteMapping("/del-account/{id}")
    @ApiOperation("删除记录")
    public ResponseResult<String> deleteAccount(@PathVariable Integer id) {
        if (!accounttbService.exists(id)) {
            logger.info("删除不存在的记录: " + id);
            return ResponseResult.fail("no such records");
        }
        accounttbService.deleteAccount(id);
        logger.info("删除记录: " + id);
        return ResponseResult.success("delete succeed!");
    }
}
