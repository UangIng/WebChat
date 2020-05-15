package com.hans.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.entity.Log;
import com.hans.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author hansing
 * @TODO: 2020/5/7
 */
public interface LogService extends IService<Log> {

    IPage<Log> pageLog(String userid,Integer pageIndex,Integer pageSize);

    int addLoginLog(User user,HttpServletRequest request);

    int addUpdateLog(User user,HttpServletRequest request);

    int addUpdatePasswordLog(User user,HttpServletRequest request);

    int addUpdateHeaderLog(User user,HttpServletRequest request);

    int addOutlineLog(User user,HttpServletRequest request);
}
