package com.zx.controller;

import com.alibaba.fastjson.JSONObject;
import com.zx.bean.JsonResult;
import com.zx.service.TestDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    @Resource
    private TestDBService testDBService;

    @RequestMapping(value = "/health",method = RequestMethod.POST)
    public JsonResult test(@RequestBody JSONObject object){

        String phoneNum = object.getString("phone_num");

        String result = testDBService.test(phoneNum);

        JsonResult jsonResult = new JsonResult();
        
        return jsonResult;
    }
}
