package com.hans.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.common.utils.CommonDate;
import com.hans.common.utils.NetUtil;
import com.hans.common.utils.WordDefined;
import com.hans.entity.Log;
import com.hans.entity.User;
import com.hans.mapper.LogMapper;
import com.hans.service.LogService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author hansing
 * @TODO: 2020/5/7
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public IPage<Log> pageLog(String userid,Integer pageIndex,Integer pageSize) {
        Wrapper<Log> qw = new QueryWrapper<>();
        Log log = new Log();
        log.setUserid(userid);
        qw.equals(log);
        Page<Log> page = new Page<>(pageIndex,pageSize);
        IPage<Log> iPage = baseMapper.selectPage(page,qw);
//        Integer count = baseMapper.selectCount(qw);
//        iPage.setCurrent(count);
        return iPage;
    }

    public Log initLog(User user, HttpServletRequest request) {
        Log log = new Log();
        log.setUserid(user.getUserid());
        log.setTime(new CommonDate().getTime24());
        log.setIp(NetUtil.getIpAddress(request));
        log.setId(UUID.randomUUID().toString().trim().replaceAll("-", "").toLowerCase());
        return log;
    }

    @Override
    public int addLoginLog(User user, HttpServletRequest request) {
        Log log = initLog(user, request);
        log.setType(WordDefined.LOG_TYPE_LOGIN);
        log.setDetail(WordDefined.LOG_DETAIL_USER_LOGIN);
        return baseMapper.insert(log);
    }

    @Override
    public int addOutlineLog(User user, HttpServletRequest request) {
        Log log = initLog(user, request);
        log.setType(WordDefined.LOG_TYPE_LOGOUT);
        log.setDetail(WordDefined.LOG_DETAIL_USER_LOGOUT);
        return baseMapper.insert(log);
    }

    @Override
    public int addUpdateLog(User user, HttpServletRequest request) {
        Log log = initLog(user, request);
        log.setType(WordDefined.LOG_TYPE_UPDATE);
        log.setDetail(WordDefined.LOG_DETAIL_UPDATE_PROFILE);
        return baseMapper.insert(log);
    }

    @Override
    public int addUpdatePasswordLog(User user, HttpServletRequest request) {
        Log log = initLog(user, request);
        log.setType(WordDefined.LOG_TYPE_UPDATE);
        log.setDetail(WordDefined.LOG_DETAIL_UPDATE_PASSWORD);
        return baseMapper.insert(log);
    }

    @Override
    public int addUpdateHeaderLog(User user, HttpServletRequest request) {
        Log log = initLog(user, request);
        log.setType(WordDefined.LOG_TYPE_UPDATE);
        log.setDetail(WordDefined.LOG_DETAIL_UPDATE_PROFILEHEAD);
        return baseMapper.insert(log);
    }


}
