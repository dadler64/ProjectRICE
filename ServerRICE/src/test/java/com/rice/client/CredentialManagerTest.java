package com.rice.client;

import com.rice.server.User;
import com.rice.server.UserStatus;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ALL")
class CredentialManagerTest {
    private final List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        users.add(new User("dadler", "password", UserStatus.LOGGED_OFF));
        users.add(new User("mcgoverns2", "password1", UserStatus.LOGGED_OFF));
        users.add(new User("sheltonz", "password2", UserStatus.LOGGED_ON));
        users.add(new User("rando", "rando", UserStatus.BLOCKED));
    }

    @Test
    void testValidUser() {
        assertEquals(true, validateCredentials(users, new Pair<String, String>("dadler", "password")));
    }

    @Test
    void testIncorrectPassword() {
        assertEquals(false, validateCredentials(users, new Pair<String, String>("dadler", "pass")));
    }

    @Test
    void testIncorrectUsername() {
        assertEquals(false, validateCredentials(users, new Pair<String, String>("mcgoverns", "password1")));
    }

    @Test
    void testLoggedInUser() {
        assertEquals(false, validateCredentials(users, new Pair<String, String>("sheltonz", "password2")));

    }

    @Test
    void testBlockedUser() {
        assertEquals(false, validateCredentials(users, new Pair<String, String>("rando", "rando")));
    }

    private boolean validateCredentials(List<User> userList, Pair<String, String> credentials) {
        for (User user : userList) {
            if (user.getUsername().equals(credentials.getKey())) {
                if (user.getPassword().equals(credentials.getValue())) {
                    if (user.getStatus() == UserStatus.LOGGED_OFF) {
                        System.out.println("Correct username and password.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}