package com.meow.accountant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meow.accountant.entity.Accounttb;

/**
* @author 凌洛
* @description 针对表【accounttb】的数据库操作Service
* @createDate 2022-11-10 16:34:55
*/
public interface AccounttbService extends IService<Accounttb> {

    void addAccount(Accounttb accounttb);

    void updateAccount(Accounttb accounttb);

    void deleteAccount(Integer id);

    boolean exists(Integer id);
}
