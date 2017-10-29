package com.sitp.questioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * Created by jieping on 2017-07-08.
 */
@SpringBootApplication
@ServletComponentScan
@EnableWebSocket
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
