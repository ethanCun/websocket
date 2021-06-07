package com.rjet.websocket.controller;

import com.rjet.websocket.base.BaseController;
import com.rjet.websocket.base.BaseRep;
import com.rjet.websocket.server.WebsocketServer;
import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController extends BaseController {

    @Autowired
    private WebsocketServer websocketServer;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/send/{userId}")
    public BaseRep sendMsg(String message, @PathVariable("userId") String userId){

        try {
            this.websocketServer.sendMessageInfo(message, userId);
            return BaseRep.success();
        } catch (IOException e) {
            e.printStackTrace();
            return BaseRep.fail();
        }
    }


    public static void main(String[] args) {

        //堆中已使用内存
        System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024.0);
        System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024.0);

        ConcurrentHashMap<String, WebSocketController> stringWebSocketControllerConcurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 10000; i++) {

            stringWebSocketControllerConcurrentHashMap.put(String.valueOf(i), new WebSocketController());
        }

        //计算指定对象本身在堆空间的大小，单位字节
        System.out.println(RamUsageEstimator.shallowSizeOf(stringWebSocketControllerConcurrentHashMap));

        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        System.out.println(RamUsageEstimator.sizeOf(stringWebSocketControllerConcurrentHashMap)/1024.0 + " KB");

        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        System.out.println(RamUsageEstimator.humanSizeOf(stringWebSocketControllerConcurrentHashMap));

    }
}
