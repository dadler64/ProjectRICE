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

package gson.examples.e2;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class TaskTest {

    public static void main(String[] args) {
        // Create a list of Tasks
        List<Task> list = new ArrayList<Task>();
        // Populate list of Tasks
        for (int i = 0; i < 20; i++) {
            list.add(new Task(i, "Test1", "Test2", Task.Status.ASSIGNED, 10));
        }
        // Instantiate Gson Object
        Gson gson = new Gson();

        Type type = new TypeToken<List<Task>>() {}.getType();
        String json = gson.toJson(list, type);
        System.out.println(json);
        List<Task> fromJson = gson.fromJson(json, type);

        for (Task task : fromJson) {
            System.out.println(task);
        }
    }
}