package com.meow.accountant.controller;

import com.meow.accountant.entity.response.ResponseResult;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/accountant-meow")
public class CachingController {
    @Resource
    private CacheManager cacheManager;

    @DeleteMapping("/clearcaches")
    public ResponseResult<Void> clearAllCaches() {
        cacheManager
                .getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        return ResponseResult.success();
    }
}
