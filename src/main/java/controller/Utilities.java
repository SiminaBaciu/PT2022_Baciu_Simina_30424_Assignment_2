package controller;

import GUI.SimulationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utilities {

    private SimulationFrame ui;
    private Thread threadM;
    private File log;

    public Utilities(SimulationFrame ui) {
        this.ui = ui;
        ui.addSimulatinListener(new SimulationListener());
    }

    class SimulationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int timeLimit = ui.getTimeLimit();
            int[] arrivalTimeInterval = ui.getArrivalTimeInterval();
            int[] serviceTimeInteval = ui.getServiceTimeInterval();
            int noServers = ui.getServers();
            int noTasks = ui.getTasks();
            threadM = new Thread(new SimulationManager(timeLimit, arrivalTimeInterval, serviceTimeInteval, noServers, noTasks, ui,Utilities.this));
            createLog();
            threadM.start();
        }

    }

    public void createLog() {
        log = new File("Log.txt");
    }

    public void logging(int currentTime, String tasksList, String servers) {
        try {
            FileWriter writer = new FileWriter("Log.txt", true);
            writer.append("Tasks: " + tasksList + "\n" + "Time: " + currentTime + "\n" + servers + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}