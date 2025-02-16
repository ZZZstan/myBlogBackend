package uno.stan.myblogBack.websocket;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uno.stan.myblogBack.service.WebService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/ws/chat/{userId}")
public class WebSocketServer {

//    @Resource
//    private WebService webService; 因为websocket是多对多的而spring管理的bean是单例的,使用注入失败(还是不懂),要用其他方法

    private static WebService webService;

    @Autowired
    public void setWebService(WebService webService) {
        WebSocketServer.webService = webService; // 静态字段注入
    }

    //存放会话对象
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        System.out.println("客户端：" + userId + "建立连接");
        sessionMap.put(userId, session);
        //广播在线人数
        broadcastOnlineCount();
        //获得最近的五十条聊天记录历史
        List<String> messageList=webService.getChatHistory();
        messageList.forEach(message->{
            sendToClient(userId,"message",message);
        });
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        System.out.println("收到来自客户端：" + userId + "的信息:" + message);
        sendToAllClient("message",message);
        webService.saveChatHistory(message);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param userId
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        System.out.println("连接断开:" + userId);
        sessionMap.remove(userId);
        broadcastOnlineCount();
    }


    /**
     * 群发
     *
     * @param data
     */
    public void sendToAllClient(String type,String data) {
        String message;
        if ("message".equals(type)) {
            // 消息类型不需要额外的引号，因为 data 已经是 JSON 字符串
            message = String.format("{\"type\": \"%s\", \"data\": %s}", type, data);
        } else {
            // 系统消息需要引号，因为是普通字符串
            message = String.format("{\"type\": \"%s\", \"data\": \"%s\"}", type, data);
        }
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 广播当前在线人数
     */
    public void broadcastOnlineCount() {
        int onlineCount = sessionMap.size();
        sendToAllClient("system", String.valueOf(onlineCount)); // 广播在线人数
    }

    /**
     * 向指定客户端发送消息
     * @param userId  目标用户ID
     * @param type    消息类型（例如 "message" 或 "system"）
     * @param data    消息内容（根据类型决定是否转JSON）
     * @return 是否发送成功
     */
    public void sendToClient(String userId, String type, String data) {
        Session session = sessionMap.get(userId);
        try {
            // 构造与 sendToAllClient 一致的协议格式
            String message;
            if ("message".equals(type)) {
                message = String.format("{\"type\": \"%s\", \"data\": %s}", type, data);
            } else {
                message = String.format("{\"type\": \"%s\", \"data\": \"%s\"}", type, data);
            }

            // 同步发送消息（确保线程安全）
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
            System.out.println("已发送消息至用户 " + userId + " : " + message);
        } catch (IOException e) {
            System.err.println("向用户 " + userId + " 发送消息失败: " + e.getMessage());
        }
    }

}
