package controller;

import model.Task;
import GUI.SimulationFrame;

import java.util.*;

public class SimulationManager implements Runnable {

    private  Utilities util;
    private Scheduler scheduler;
    private SimulationFrame ui;
    private int timeLimit;
    private int[] processingTimeInterval;
    private int[] arrivalTimeInterval;
    private int noServers;
    private int noTasks;
    private ArrayList<Task> generatedTasks;

    private float averageWaitingTime;
    private float averageServiceTime;
    private int peakHour;
    private ArrayList<Task> removedTasks = new ArrayList<Task>();

    public SimulationManager(int timeLimit, int[] arrivalTimeInterval, int[] processingTimeInterval, int noServers, int noTasks, SimulationFrame ui, Utilities util) {
        this.timeLimit = timeLimit;
        this.arrivalTimeInterval = arrivalTimeInterval;
        this.processingTimeInterval = processingTimeInterval;
        this.noServers = noServers;
        this.noTasks = noTasks;

        this.scheduler = new Scheduler(noServers);
        this.generatedTasks = generateNRandomTasks();
        this.averageServiceTime = calculateAvgServiceTime();
        this.util = util;
        this.ui = ui;
    }

    public int createRandom(int[] interval) {
        return (int) ((Math.random() * (interval[1] - interval[0])) + interval[0]);
    }

    public ArrayList<Task> generateNRandomTasks() {
        ArrayList<Task> randomizedTasks = new ArrayList<Task>();
        for (int i = 0; i < noTasks; i++) {
            Task t = new Task(i, createRandom(arrivalTimeInterval), createRandom(processingTimeInterval));
            randomizedTasks.add(t);
        }
        Collections.sort(randomizedTasks, new SortClients());
        return randomizedTasks;
    }

    @Override
    public void run() {
        int currentTime = 0;
        int maxWaitingTime = 0;
        while (currentTime < timeLimit) {
            ArrayList<Task> arrTasks = new ArrayList<Task>();
            for (Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    arrTasks.add(t);
                }
            }
            removedTasks.addAll(arrTasks);
            generatedTasks.removeAll(arrTasks);
            currentTime++;

            ui.createServers(scheduler.getServers().size());
            ui.updateView(scheduler.getServers(), removedTasksString(), currentTime, tasksToString());
            util.logging(currentTime, tasksToString(), scheduler.toString());

            averageWaitingTime = (float) (averageWaitingTime + scheduler.getAverageWaitingTime());
            if (scheduler.getCurrentWaitingTime() > maxWaitingTime) {
                peakHour = currentTime;
                maxWaitingTime = scheduler.getCurrentWaitingTime();
            }
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        averageWaitingTime = averageWaitingTime / timeLimit;
        ui.setPeakHourText(Double.toString(peakHour));
        ui.setAvgWaitingTimeText(Double.toString(averageWaitingTime));
        ui.setAvgServiceTimeText(Double.toString(averageServiceTime));
    }

    public float calculateAvgServiceTime() {
        float serviceTime = 0;
        for (Task t : generatedTasks) {
            serviceTime = serviceTime + t.getServiceTime();
        }
        serviceTime = serviceTime / generatedTasks.size();
        return serviceTime;
    }

    public static class SortClients implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getArrivalTime() - t2.getArrivalTime();
        }
    }

    public ArrayList<Task> getRemovedTasks() {
        return removedTasks;
    }

    public String tasksToString() {
        String output = "";
        for (Task t : generatedTasks) {
            output = output + t.toString() + "\n";
        }
        return output;
    }

    public String removedTasksString() {
        String out = "";
        for (Task t : removedTasks)
            out = out + t.toString() + '\n';
        return out;
    }
}
