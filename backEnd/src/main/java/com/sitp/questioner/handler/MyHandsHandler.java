package com.sitp.questioner.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Component
public class MyHandsHandler extends DefaultHandshakeHandler {


    public MyHandsHandler() {
    }

    public MyHandsHandler(RequestUpgradeStrategy requestUpgradeStrategy) {
        super(requestUpgradeStrategy);
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        URI uri = request.getURI();
        String id = null;
        if (uri != null) {
            String query = uri.getPath();
            String[] pairs = query.split("/");
            id = pairs[3];
        }
        MyPrincipal principal = new MyPrincipal();
        principal.setName(id);
        System.out.println(id);
        return principal;
    }

    class MyPrincipal implements Principal {
        private String username;

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

            MyPrincipal that = (MyPrincipal) o;

            return username != null ? username.equals(that.username) : that.username == null;

        }

        @Override
        public int hashCode() {
            return username != null ? username.hashCode() : 0;
        }
    }
}