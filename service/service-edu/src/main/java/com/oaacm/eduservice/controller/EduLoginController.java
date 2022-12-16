package com.oaacm.eduservice.controller;

import com.oaacm.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin//跨域
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("login")
    public R login() {

        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {

        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://acmlab.oss-cn-qingdao.aliyuncs.com/%E5%A4%B4%E5%83%8F.jpg");
    }



}
