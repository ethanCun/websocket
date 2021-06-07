package com.rjet.websocket.server;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebsocketServer {

    //保存session 和 socket <用户id， websocket> 保证static单例
    private static ConcurrentHashMap<String, WebsocketServer> websocketMap = new ConcurrentHashMap<String, WebsocketServer>();

    private Session session;
    private String userId;

    /**
     * 建立成功调用的方法
     * @PathParam websocket 用于获取 #{} 中的值
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){

        //每次建立链接的时候记录当前会话
        this.session = session;
        this.userId = userId;

        //建立链接，主动发生消息
        try {
            this.sendMessage("hello");

            //将socket会话保存到map key为业务id  value为当前对象
            if (websocketMap.containsKey(this.userId)){
                //重新保存绘画
                this.websocketMap.remove(this.userId);
                this.websocketMap.put(this.userId, this);
            }else {
                this.websocketMap.put(this.userId, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭
     */
    @OnClose
    public void OnClose(){

        if (this.websocketMap.contains(userId)) {
            this.websocketMap.remove(userId);
        }

        System.out.println("关闭socket");
    }

    /**
     * 收到客户端消息后调用的方法
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message){

        System.out.println("受到客户端消息: userId: " + this.userId + " 消息内容: " + message);
    }

    /**
     * 发生错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){

        System.out.println("发生了错误: " + error);
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 服务器主动推送消息
     */
    public void sendMessageInfo(String message, String userId) throws IOException {

        if (this.websocketMap.containsKey(userId)) {
            this.websocketMap.get(userId).sendMessage(message);
        }

    }


}
