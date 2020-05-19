package com.hans.api.controller;


import com.hans.common.utils.CommonDate;
import com.hans.common.utils.RestResult;
import com.hans.common.utils.WordDefined;
import com.hans.entity.User;
import com.hans.service.LogService;
import com.hans.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResult login(String userid, String password, HttpServletRequest request) {
        User user = userService.getUserByUserid(userid);
        if (user == null) {
            //用户不存在
            return RestResult.buildError(WordDefined.LOGIN_USERID_ERROR);
        } else {
            if (!user.getPassword().equals(password)) {
                //密码不对
                return RestResult.buildError(WordDefined.LOGIN_PASSWORD_ERROR);
            } else {
                if (user.getStatus() == -1) {
                    //禁止登陆
                    return RestResult.buildError(WordDefined.LOGIN_USERID_DISABLED);
                } else {
                    logService.addLoginLog(user, request);
                    user.setLasttime(new CommonDate().getTime24());
                    userService.updateUser(user);
                    //实际项目要生成一个key保存在redis等缓存中并设置超时时间,session中存放的是key,并不是user对象
                    request.getSession().setAttribute("userid", user.getUserid());
                    return RestResult.buildSuccess(WordDefined.LOGIN_SUCCESS, user);
                }
            }
        }
    }

    @RequestMapping(value = "/n", method = RequestMethod.GET)
    public RestResult outline(HttpServletRequest request) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        User user = userService.getUserByUserid(userid);
        logService.addOutlineLog(user, request);
        request.getSession().removeAttribute("userid");
        return RestResult.buildSuccess();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public RestResult getInfo(HttpServletRequest request) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        User user = userService.getUserByUserid(userid);
        return RestResult.buildSuccess(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResult update(User user, HttpServletRequest request) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }
        userService.updateUser(user);
        logService.addUpdateLog(user, request);
        return RestResult.buildSuccess(user);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public RestResult updatePassword(String oldPassword, String newPassword, String surePassword, HttpServletRequest request) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }
        String userid = request.getSession().getAttribute("userid").toString();
        User user = userService.getUserByUserid(userid);
        if (!user.getPassword().equals(oldPassword)) {
            return RestResult.buildError("原始密码不正确");
        }

        if (!newPassword.equals(surePassword)) {
            return RestResult.buildError("新密码两次输入不一致");
        }

        user.setPassword(newPassword);
        userService.updateUser(user);
        logService.addUpdatePasswordLog(user, request);
        return RestResult.buildSuccess();
    }

    @RequestMapping(value = "/getImgByUser", method = RequestMethod.GET)
    public void getImgByUserid(String userid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (userid == null) {
            return;
        }
        User user = userService.getUserByUserid(userid);
        if (user == null) {
            return;
        }
        getFile(user.getProfilehead(), request, response);
    }


    @RequestMapping(value = "/rawImgByUUID", method = RequestMethod.GET)
    public void getFile(String fullName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (fullName == null || fullName.trim().equals("")) {
            return;
        }

        String realPath = new String("chat_room_api/src/main/resources/file/");
        File file = new File(realPath + fullName);
        if (!file.exists()) {
            return;
        }
        FileInputStream inputStream = null;
        OutputStream stream = null;

        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data);
            response.setContentType("image/png");
            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
        } catch (Exception e) {

        } finally {
            if (inputStream != null) inputStream.close();

            if (stream != null) stream.close();
        }
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestResult uploadFile(MultipartFile uploadFile, HttpServletRequest request) {
        if (request.getSession().getAttribute("userid") == null) {
            return RestResult.buildError("未登录");
        }

        if (null == uploadFile) {
            return RestResult.buildError("上传失败，无法找到文件！");
        }

        // BMP、JPG、JPEG、PNG、GIF
        String fileName = uploadFile.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".bmp") && !fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")
                && !fileName.endsWith(".gif")) {
            return RestResult.buildError("上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！");
        }

        String userid = request.getSession().getAttribute("userid").toString();
        User user = userService.getUserByUserid(userid);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("chat_room_api/src/main/resources/file/");
        logger.info("-----------上传文件保存的路径【" + realPath + "】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        logger.info("-----------存放上传文件的文件夹【" + file + "】-----------");
        logger.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【" + file.getAbsolutePath() + "】-----------");
        if (!file.isDirectory()) {
            //递归生成文件夹
            file.mkdirs();
        }

        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        logger.info("-----------文件原始的名字【" + oldName + "】-----------");
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        logger.info("-----------文件要保存后的新名字【" + newName + "】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
            logger.info("-----------【" + filePath + "】-----------");
            user.setProfilehead(format + newName);
            userService.updateUser(user);
            logService.addUpdateHeaderLog(user, request);
            return RestResult.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResult.buildError();
    }

}
