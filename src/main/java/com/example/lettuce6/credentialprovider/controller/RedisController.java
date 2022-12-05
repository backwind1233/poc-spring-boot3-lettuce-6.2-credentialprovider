package com.example.lettuce6.credentialprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test/redis/multiSet")
    @ResponseBody
    public String testRedisSet() {
        Map valueMap = new HashMap();
        valueMap.put("valueMap1","map1");
        valueMap.put("valueMap2","map2");
        valueMap.put("valueMap3","map3");
        redisTemplate.opsForValue().multiSet(valueMap);
        return "Run testRedisSet() success.";
    }

}
