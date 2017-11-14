package com.sitp.questioner.config;

import com.alibaba.fastjson.JSON;
import com.sitp.questioner.util.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qi on 2017/10/26.
 */
@ServerEndpoint(value = "/websocket/notice")
public class NoticeWebSocket {

    private static Logger logger = LoggerFactory.getLogger(NoticeWebSocket.class);

    private String userId;

    private static final ConcurrentHashMap<String, RemoteEndpoint.Basic> webSocketSessionMap
            = new ConcurrentHashMap<>();

    public static void pushMessageToOneUser(String userId, Object message) {
        webSocketSessionMap.forEach(1, (id, session) -> {
            if (id.equals(userId)) {
                try {
                    session.sendText(JSON.toJSONString(message));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        String userId = WebSocketUtil.getValueFormQueryParams(session.getQueryString(), "id");
        if (userId != null && !userId.equals("")) {
            logger.debug(userId + " has login into the line");
            this.userId = userId;
            webSocketSessionMap.putIfAbsent(userId, session.getBasicRemote());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        try {
            session.getBasicRemote().sendText("Hello");
        }
        catch (IOException e) {
            logger.error("Websocket发送消息失败！");
        }
        pushMessageToOneUser("1", "HELLO 1");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error(error.toString());
        if (this.userId != null && !this.userId.equals("")) {
            logger.error("发送错误，" + userId + "已经下线1");
        }
    }

    @OnClose
    public void onClose() {
        if (this.userId != null && !this.userId.equals("")) {
            logger.debug(userId + " has log off the line");
            webSocketSessionMap.remove(userId);
        }
    }

}
