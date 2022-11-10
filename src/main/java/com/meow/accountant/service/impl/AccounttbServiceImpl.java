package com.meow.accountant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meow.accountant.entity.Accounttb;
import com.meow.accountant.service.AccounttbService;
import com.meow.accountant.mapper.AccounttbMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 凌洛
 * @description 针对表【accounttb】的数据库操作Service实现
 * @createDate 2022-11-10 16:34:55
 */
@Service
public class AccounttbServiceImpl extends ServiceImpl<AccounttbMapper, Accounttb>
        implements AccounttbService {

    private final AccounttbMapper accounttbMapper;

    public AccounttbServiceImpl(AccounttbMapper accounttbMapper) {
        this.accounttbMapper = accounttbMapper;
    }

    @Override
    public void addAccount(Accounttb accounttb) {
        accounttbMapper.insertAll(accounttb);
    }

    @Override
    public void updateAccount(Accounttb accounttb) {
        accounttbMapper.updateSelective(accounttb);
    }

    @Override
    public void deleteAccount(Integer id) {
        accounttbMapper.deleteById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return accounttbMapper.getIdExsits(id) == 1;
    }
}




