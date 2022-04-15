package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private int waitingTime;

    public Server() {
        tasks = new ArrayBlockingQueue<Task>(250);
        this.waitingTime = 0;
    }

    public void addTask(Task t) {
        tasks.add(t);
        this.waitingTime = waitingTime + t.getServiceTime();
    }

    @Override
    public void run() {
        while (true) {
            if (tasks.isEmpty() == false) {
                try {
                    int serviceTime = tasks.peek().getServiceTime();
                    for (int i = 0; i < serviceTime; i++) {
                        Thread.sleep(1200);
                        if (this.waitingTime > 0) {
                            this.waitingTime--;
                        }
                    }
                    tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < tasks.size(); i++) {
            output = output + "Tasks ";
        }
        return output;
    }
}

