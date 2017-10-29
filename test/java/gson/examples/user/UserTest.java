/*
 * Copyright [2017] [Dan Adler <adlerd@wit.edu>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gson.examples.user;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rice.server.User;
import com.rice.server.UserStatus;


public class UserTest {

    public static void main(String[] args) {
        // Create a list of Tasks
        List<User> list = new ArrayList<User>();
        // Populate list of Tasks
        for (int i = 0; i < 20; i++) {
            list.add(new User("username", "password", UserStatus.LOGGED_OFF));
        }
        // Instantiate Gson Object
        Gson gson = new Gson();

        Type type = new TypeToken<List<User>>() {}.getType();
        String json = gson.toJson(list, type);
        System.out.println(json);
        List<User> fromJson = gson.fromJson(json, type);

        for (User user : fromJson) {
            System.out.println(user);
        }
    }
}