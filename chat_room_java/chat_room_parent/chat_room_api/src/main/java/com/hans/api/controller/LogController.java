package com.hans.api.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hans.common.utils.RestResult;
import com.hans.entity.Log;
import com.hans.entity.User;
import com.hans.service.LogService;
import com.hans.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/log")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);


    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public RestResult logs(HttpServletRequest request, Integer pageIndex, Integer pageSize) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        IPage<Log> iPage = logService.pageLog(userid, pageIndex, pageSize);
        return RestResult.buildSuccess(iPage);
    }

}
