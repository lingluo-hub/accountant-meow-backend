package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.util.MapUtils;
import com.meow.accountant.entity.Budget;
import com.meow.accountant.entity.response.ResponseResult;
import com.meow.accountant.service.impl.BudgetServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 凌洛
 * @Description <p>预算 controller 层</p>
 * <p>budget：double, 预算额</p>
 * <p>userid: 用户id唯一标识，区别记录</p>
 */
@RestController
@RequestMapping("/api/v2/")
public class BudgetController {

    private final MapSearcher mapSearcher;
    private final BudgetServiceImpl budgetService;

    public BudgetController(MapSearcher mapSearcher, BudgetServiceImpl budgetService) {
        this.mapSearcher = mapSearcher;
        this.budgetService = budgetService;
    }

    Logger logger = LogManager.getLogger("执行记账操作");

    @GetMapping("/index-budget")
    @ApiOperation("全局搜索预算")
    public SearchResult<Map<String, Object>> index(Double budget, @RequestParam String userid) {
        Map<String, Object> params = MapUtils.builder()
                .field(Budget::getUserid, userid)
                .field(Budget::getBudget, budget)
                .build();
        logger.info("查询预算: " + userid);
        return mapSearcher.search(Budget.class, params);
    }

    @PostMapping("/budget")
    @ApiOperation("插入/更新预算")
    public ResponseResult<String> budget(Budget budget) {
        if (budgetService.exists(budget.getUserid())) {
            budgetService.updateBudget(budget);
            logger.info("更新预算: " + budget.getUserid());
            return ResponseResult.success("update succeed!");
        }
        budgetService.insertBudget(budget);
        logger.info("新增预算: " + budget.getUserid());
        return ResponseResult.success("insert succeed!");
    }
}
