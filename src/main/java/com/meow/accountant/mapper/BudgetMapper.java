package com.meow.accountant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.meow.accountant.entity.Budget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 凌洛
 * @description 针对表【budget】的数据库操作Mapper
 * @createDate 2022-11-10 20:19:06
 * @Entity com.meow.accountant.entity.Budget
 */
@Mapper
public interface BudgetMapper extends BaseMapper<Budget> {

    int insertAll(Budget budget);

    int updateSelective(Budget budget);

    int getIdExsits(String userid);
}




