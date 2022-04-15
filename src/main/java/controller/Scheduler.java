package controller;

import controller.Strategy;
import controller.TimeStrategy;
import model.Task;
import model.Server;

import java.util.ArrayList;

public class Scheduler {

    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxQueues) {
        servers = new ArrayList<Server>();
        for (int i = 0; i < maxQueues; i++) {
            Server s = new Server();
            Thread t = new Thread(s);
            t.start();
            this.servers.add(s);
        }
        this.strategy = new TimeStrategy();
    }

    public void dispatchTask(Task t) {
        strategy.addTask(servers, t);
    }

    public ArrayList<Server> getServers() {
        return this.servers;
    }

    public double getAverageWaitingTime() {
        int waitingTime = 0;
        for (Server q : servers) {
            waitingTime = waitingTime + q.getWaitingTime();
        }
        if (servers.size() != 0) {
            return waitingTime / servers.size();
        } else {
            return 0;
        }
    }

    public int getCurrentWaitingTime() {
        int waitingTime = 0;
        for (Server s : servers) {
            waitingTime = waitingTime + s.getWaitingTime();
        }
        return waitingTime;
    }


    public String toString() {
        String output = "";
        int index = 1;
        for (Server q : servers) {
            output += "Server " + index + ": ";
            for (Task c : q.getTasks()) {
                output += c.toString() + " ";
            }
            output += "\n";
            index++;
        }
        return output;
    }
}
