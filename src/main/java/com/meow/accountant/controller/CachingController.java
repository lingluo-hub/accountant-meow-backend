package com.meow.accountant.controller;

import com.meow.accountant.entity.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 凌洛
 * @Description 清空 Redis 缓存
 */
@Api
@RestController
@RequestMapping("/accountant-meow")
public class CachingController {
    @Resource
    private CacheManager cacheManager;

    Logger logger = LogManager.getLogger("清空缓存");

    @ApiOperation("清空缓存")
    @DeleteMapping("/clearcaches")
    public ResponseResult<Void> clearAllCaches() {
        cacheManager
                .getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        logger.info("clear ALL cache");
        return ResponseResult.success();
    }
}
