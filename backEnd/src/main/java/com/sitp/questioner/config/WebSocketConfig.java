package com.sitp.questioner.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * Created by qi on 2017/10/22.
 */

@Configuration
public class WebSocketConfig {
    private static int num = 0;
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
