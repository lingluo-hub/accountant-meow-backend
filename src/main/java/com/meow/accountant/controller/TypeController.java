package com.meow.accountant.controller;

import com.meow.accountant.entity.Type;
import com.meow.accountant.service.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lihangqi
 */
@RestController
@RequestMapping("/accountant-meow")
public class TypeController {

    @Autowired
    private TypeServiceImpl typeService;

    @PostMapping("/insertType")
    public void insertType(@RequestParam String typename, @RequestParam int imageId, @RequestParam int sImageId, @RequestParam int kind){
        typeService.insertType(typename, imageId, sImageId, kind);
    }

    @RequestMapping("/getAllType")
    public List getAllType(){
        //拿到service的queryUserList方法
        return typeService.queryTypeList();
    }

    @RequestMapping("/getTypeByKind")
    public List getTypeByKind(@RequestParam int kind){
        return typeService.getTypeByKind(kind);
    }
}
