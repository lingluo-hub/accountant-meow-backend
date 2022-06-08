package com.meow.accountant.service;

import com.meow.accountant.dao.TypeDAO;
import com.meow.accountant.entity.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lihangqi
 */
@Service
public class TypeServiceImpl implements TypeDAO {

    Logger logger = LogManager.getLogger("执行类型数据操作");

    @Autowired
    private TypeDAO typeDAO;


    @Override
    public void insertType(String typename, int imageId, int sImageId, int kind) {
        typeDAO.insertType(typename, imageId, sImageId, kind);
        logger.info("插入类型数据");
    }

    @Override
    public List<Type> queryTypeList() {
        //调用实现dao接口，返回我们写的唯一一条查询全部方法
        logger.info("查询所有类型数据");
        return typeDAO.queryTypeList();
    }

    @Override
    public List<Type> getTypeByKind(@RequestParam int kind) {
        String kindlog = (kind == 0) ? "支出" : "收入";
        logger.info("根据收入/支出查询类型: " + kindlog);
        return typeDAO.getTypeByKind(kind);
    }
}
