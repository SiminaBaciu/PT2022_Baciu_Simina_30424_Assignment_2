package controller;

import model.Task;
import model.Server;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        servers.get(servers.size() - 1).addTask(t);
        Collections.sort(servers, new SortByWaitingTime().reversed());
    }

    public static class SortByWaitingTime implements Comparator<Server> {
        @Override
        public int compare(Server s1, Server s2) {
            return s1.getWaitingTime() - s2.getWaitingTime();
        }
    }
}
