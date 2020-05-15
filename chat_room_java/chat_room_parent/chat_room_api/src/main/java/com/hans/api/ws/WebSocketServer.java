package com.hans.api.ws;


import com.alibaba.fastjson.JSONObject;
import com.hans.common.utils.CommonDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * @author hansing
 * @TODO: 2020/5/7
 */
@ServerEndpoint(value = "/ws", configurator = GetHttpSessionConfigurator.class)
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 存放所有在线的客户端
     * HashMap线程不安全
     * HashTable线程安全，但效率低下
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static int onlineCount = 0;//记录连接数
    private Session session;    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String userid;      //用户名
    private HttpSession httpSession;    //request的session

    private static List list = new ArrayList<>();   //在线列表,记录用户名称
    private static Map<String, String> routetab = new HashMap<>();  //用户名和websocket的session绑定的路由表

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //将新用户存入在线的组
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.userid = (String) httpSession.getAttribute("userid");    //获取当前用户
        clients.put(session.getId(), session);//客户端列表
        list.add(this.userid);//用户名列表
        routetab.put(session.getId(), this.userid);//session userid绑定
        addOnlineCount();
        log.info("有新的客户端连接了: {}", session.getId() + ",用户名id" + userid);
        String msg = "[" + this.userid + "]加入聊天室,当前在线人数为" + getOnlineCount() + "位";
        sendNotice(msg);
    }


    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        System.out.println(session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());//清楚session
        String curr_userid = routetab.get(session.getId());//获取用户id
        list.remove(curr_userid);//移除用户名
        routetab.remove(session.getId());//解除绑定关系
        subOnlineCount();
        if (clients.size() > 0) {
            String msg = "[" + this.userid + "]离开聊天室,当前在线人数为" + getOnlineCount() + "位";
            sendNotice(msg);
        }
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("服务端收到客户端发来的消息: {}", message);
        JSONObject msg = JSONObject.parseObject(message);
        WsResultMessage res = new WsResultMessage();
        res.setFrom(msg.getString("from"));
        res.setType(WsTypeEnum.MESSAGE);
        res.setMessage(msg.getString("content"));
        res.setTime(new CommonDate().getTime24());
        if (msg.getString("to") == null) {
            //广播
            this.sendAll(JSONObject.toJSONString(res));
        } else {
            //指定发送
            session = clients.get(msg.getString("to"));
            if (session != null) {
                sendTo(JSONObject.toJSONString(res));
            }
        }
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    private void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }

    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    private void sendTo(String message) {
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送通知
     *
     * @param message 消息对象
     */
    private void sendNotice(String message) {
        WsResultMessage res = new WsResultMessage();
        res.setFrom(this.userid);
        res.setType(WsTypeEnum.NOTICE);
        res.setMessage(message);
        res.setData(list);
        res.setTime(new CommonDate().getTime24());
        sendAll(JSONObject.toJSONString(res));
    }


    public int getOnlineCount() {
        return onlineCount;
    }

    public void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
