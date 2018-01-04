package com.wangli.controller;


import com.wangli.service.RedisCacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangli
 */
@Controller
public class ViewController {
    private static final Logger logger = Logger.getLogger(ViewController.class);

    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping("/view")
    public String view() {
        logger.info("您已通过springMVC进入controller方法");
        logger.info("------存储数据------");
        redisCacheService.putSessionObject("123", "zoeSoft");
        logger.info("------读取数据------");
        redisCacheService.getSessionObject("123");
        logger.info("------删除数据------");
        redisCacheService.clearSessionObject("123");
        logger.info("------删除后再次读取数据------");
        redisCacheService.getSessionObject("123");
        return "index";
    }
}
