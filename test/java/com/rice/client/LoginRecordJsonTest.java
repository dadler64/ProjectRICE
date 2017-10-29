package com.rice.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRecordJsonTest {
    private String ip = "127.0.0.1";
    private int port = 6625;
    private String username = "user1";
    private String password = "password1";

    LoginRecord test = new LoginRecord(ip, port, username, password);
    String expected = String.format("[{\"ip\":\"%s\",\"port\":%d,\"username\":\"%s\",\"password\":\"%s\"}]", ip, port, username, password);
    @Test
    void toJson() {
        assertEquals(expected, test.toJson());
    }
}