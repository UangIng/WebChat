package com.hans.api.ws;

import lombok.Data;


/**
 * @author hansing
 * @TODO: 2020/5/7
 */
@Data
public class WsResultMessage {


    private String from;
    private String to;
    private WsTypeEnum type;
    private String message;
    private String time;
    private Object data;

}
