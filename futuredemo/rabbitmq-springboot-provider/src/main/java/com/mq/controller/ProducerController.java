package com.mq.controller;

import com.mq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author TanYu
 * @create 2019/11/12 15:13
 * @description
 */
@Controller
public class ProducerController {
    @Autowired
    ProducerService producerService;

    @RequestMapping("/send")
    @ResponseBody
    public String test(){
       return   producerService.sendMessage();
//        return "success";
    }

}
