package com.pysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {


    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "SBB 시작 페이지";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello ^ ^";
    }

}
