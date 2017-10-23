package com.sitp.questioner.config;

import com.sitp.questioner.handler.MyHandsHandler;
import com.sitp.questioner.util.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.messaging.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by qi on 2017/10/22.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
    }


    /*
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String jwtToken = accessor.getFirstNativeHeader("Auth-Token");
                    logger.debug("webSocket token is {}", jwtToken);
                    if (jwtToken!=null && !jwtToken.equals("")) {
                        Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                        sessionAttributes.put("username", jwtToken);
                        WebSocketPrincipal principal = new WebSocketPrincipal();
                        principal.setName(jwtToken);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,null);
                        accessor.setUser(authenticationToken);
                        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                    }
                }
                return message;
            }
        });
    }

     */


    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
                return new WebSocketHandlerDecorator(webSocketHandler) {
                    @Override
                    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
                        String username = WebSocketUtil.getUsernameFormWebSocketUrl(webSocketSession.getUri().toString());
                        System.out.println("inline: " + username);
                        super.afterConnectionEstablished(webSocketSession);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
                        String username = WebSocketUtil.getUsernameFormWebSocketUrl(webSocketSession.getUri().toString());
                        logger.info("offline: " + username);
                        super.afterConnectionClosed(webSocketSession, closeStatus);
                    }
                };
            }
        });
        super.configureWebSocketTransport(registration);
    }


    private class WebSocketPrincipal implements Principal {
        private String username;

        public WebSocketPrincipal(String username) {
            this.username = username;
        }


        @Override
        public String getName() {
            return username;
        }

        public void setName(String name) {
            this.username = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WebSocketPrincipal that = (WebSocketPrincipal) o;

            return username != null ? username.equals(that.username) : that.username == null;

        }

        @Override
        public int hashCode() {
            return username != null ? username.hashCode() : 0;
        }
    }


}
