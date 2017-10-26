package com.rice.server;

public class Server {

    public static void main(String... args) {
        getUsersFromJson("Users.json");
//        do {
        User dan = new User("dan", "dadler", true);
        User zach = new User("zach", "ofpotatoes", false);
        System.out.print(User.getUsernames());

//            if (User.getNumUsers() == 0) {
//                break;
//            }
//        } while (true);
    }

    private static void getUsersFromJson(String path) {

    }
}
