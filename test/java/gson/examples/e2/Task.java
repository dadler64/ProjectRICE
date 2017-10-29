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

public class Task {
    private final long id;
    private String summary;
    private String description;
    private Status status;
    private int priority;

    public enum Status {
        CREATED, ASSIGNED, CANCELED, COMPLETED
    }

    public Task(long id, String summary, String description, Status status, int priority) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", summary=" + summary + ", description=" + description + ", status=" + status
                + ", priority=" + priority + "]";
    }

}