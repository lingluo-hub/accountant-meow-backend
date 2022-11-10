package com.meow.accountant.service;

import com.meow.accountant.entity.Budget;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 凌洛
* @description 针对表【budget】的数据库操作Service
* @createDate 2022-11-10 20:19:06
*/
public interface BudgetService extends IService<Budget> {

    void insertBudget(Budget budget);

    void updateBudget(Budget budget);

    boolean exists(String userid);

}
