package com.meow.accountant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meow.accountant.entity.Budget;
import com.meow.accountant.service.BudgetService;
import com.meow.accountant.mapper.BudgetMapper;
import org.springframework.stereotype.Service;

/**
 * @author lihan
 * @description 针对表【budget】的数据库操作Service实现
 * @createDate 2022-11-10 20:19:06
 */
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget>
        implements BudgetService {

    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetMapper budgetMapper) {
        this.budgetMapper = budgetMapper;
    }

    @Override
    public void insertBudget(Budget budget) {
        budgetMapper.insertAll(budget);
    }

    @Override
    public void updateBudget(Budget budget) {
        budgetMapper.updateSelective(budget);
    }

    @Override
    public boolean exists(String userid) {
        return budgetMapper.getIdExsits(userid) == 1;
    }
}




