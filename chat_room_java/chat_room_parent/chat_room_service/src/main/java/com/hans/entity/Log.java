package com.hans.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hansing
 * @TODO: 2020/5/6 系统日志实体类
 */
@Data
public class Log {
    @TableId(value = "id")
    private String id;      //日志编号
    private String userid;  //用户名
    private String time;    //时间
    private String type;    //类型
    private String detail;  //详情
    private String ip;      //ip地址

}
