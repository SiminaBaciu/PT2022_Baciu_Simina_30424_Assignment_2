package GUI;

import controller.SimulationManager;
import controller.Utilities;
import model.Server;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class SimulationFrame extends JFrame {

    Container container = getContentPane();

    JLabel currentTimeL = new JLabel("Current Time: ");
    JLabel tasksListLabel = new JLabel("Task List: ");
    JTextArea tasksList = new JTextArea();
    JTextArea removedTasksList = new JTextArea();
    JLabel time = new JLabel("Time: ");
    JLabel arrivalTimeInterval = new JLabel("Arrival Time: ");
    JTextField timeField = new JTextField();
    JTextField minArrivalTime = new JTextField(5);
    JTextField maxArrivalTime = new JTextField(5);
    JLabel serviceTimeInterval = new JLabel("Service Time: ");
    JTextField minServiceTime = new JTextField(5);
    JTextField maxServiceTime = new JTextField(5);
    JLabel minus = new JLabel("-");
    JLabel minus1 = new JLabel("-");
    JLabel noServers = new JLabel("Number of servers: ");
    JLabel noTasks = new JLabel("Number of tasks: ");
    JTextField noServersField = new JTextField(5);
    JTextField noTasksField = new JTextField(5);
    JLabel peakHour = new JLabel("Peak Hour is: ");
    JTextField peakHourText = new JTextField();
    JLabel avgWaitingTime = new JLabel("Average Waiting Time is: ");
    JTextField avgWaitingTimeText = new JTextField();
    JLabel avgServiceTime = new JLabel("Average Service Time is: ");
    JTextField avgServiceTimeText = new JTextField();
    JButton simulate = new JButton("Simulate");
    public ArrayList<JLabel> servers;

    private Thread threadM;
    private File log;

    public void createServers(int noServers) {
        int x = 50;
        int y = 200;//
        int w = 1500;
        int h = 500;
        int countX = 0;
        for (int i = 1; i <= noServers; i++) {
            JLabel serverI = new JLabel("Server" + i + ":");
            servers.add(serverI);
            if (countX == 5) {
                x = x + 200;
                countX = 0;
                y = 200;
            }
            serverI.setBounds(x, y, w, h);
            container.add(serverI);
            y = y + 50;
            countX++;
        }

    }

    public SimulationFrame() {

        setLayoutManag();
        setLocAndSize();
        addComponent();
        servers = new ArrayList<JLabel>();
    }

    public void setLayoutManag() {
        container.setLayout(null);
    }

    public void setLocAndSize() {
        this.currentTimeL.setBounds(50, 50, 200, 50);
        this.tasksList.setBounds(110, 125, 200, 190);
        this.removedTasksList.setBounds(480, 350, 200, 190);
        this.tasksListLabel.setBounds(50, 125, 200, 190);
        this.time.setBounds(50, 15, 75, 50);
        this.timeField.setBounds(110, 20, 30, 30);

        this.avgWaitingTime.setBounds(200, 15, 150, 50);
        this.avgWaitingTimeText.setBounds(360, 20, 30, 30);

        this.avgServiceTime.setBounds(200, 50, 150, 50);
        this.avgServiceTimeText.setBounds(360, 55, 30, 30);

        this.peakHour.setBounds(470, 15, 150, 50);
        this.peakHourText.setBounds(560, 20, 30, 30);

        this.arrivalTimeInterval.setBounds(350, 125, 150, 50);
        this.minArrivalTime.setBounds(430, 125, 50, 50);
        this.maxArrivalTime.setBounds(485, 125, 50, 50);
        this.minus.setBounds(480, 125, 10, 50);

        this.serviceTimeInterval.setBounds(350, 180, 150, 50);
        this.minServiceTime.setBounds(430, 180, 50, 50);
        this.maxServiceTime.setBounds(485, 180, 50, 50);
        this.minus1.setBounds(480, 180, 10, 50);

        this.noServers.setBounds(350, 235, 150, 50);
        this.noServersField.setBounds(485, 235, 50, 50);

        this.noTasks.setBounds(350, 290, 150, 50);
        this.noTasksField.setBounds(485, 290, 50, 50);

        this.simulate.setBounds(320, 580, 120, 50);

        peakHourText.setEditable(false);
        avgServiceTimeText.setEditable(false);
        avgWaitingTimeText.setEditable(false);
        removedTasksList.setEditable(false);
        tasksList.setEditable(false);

    }

    public void addComponent() {
        container.add(currentTimeL);
        container.add(tasksList);
        container.add(tasksListLabel);
        container.add(time);
        container.add(arrivalTimeInterval);
        container.add(timeField);
        container.add(minArrivalTime);
        container.add(maxArrivalTime);
        container.add(serviceTimeInterval);
        container.add(minServiceTime);
        container.add(minus);
        container.add(maxServiceTime);
        container.add(minus1);
        container.add(noServers);
        container.add(noTasks);
        container.add(noTasksField);
        container.add(noServersField);
        container.add(simulate);
        container.add(removedTasksList);
        container.add(peakHour);
        container.add(peakHourText);
        container.add(avgWaitingTime);
        container.add(avgWaitingTimeText);
        container.add(avgServiceTime);
        container.add(avgServiceTimeText);
    }


    public void updateView(ArrayList<Server> serverList, String removedTasks, int currentTime, String taskList) {
        int i = 0;
        currentTimeL.setText("Time: " + currentTime);

        for (Server s : serverList) {
            if (i < servers.size()) {
                if (!servers.get(i).isEnabled()) {
                    servers.get(i).setEnabled(true);
                }
                servers.get(i).setText("                       " + s.toString());
                i++;
            }
        }
        tasksList.setText("Tasks: " + '\n' + taskList);
        removedTasksList.setText("Removed Tasks: " + '\n' + removedTasks);
    }


    public int getTimeLimit() {
        return Integer.parseInt(timeField.getText());
    }

    public int[] getServiceTimeInterval() {
        return new int[]{Integer.parseInt(minServiceTime.getText()), Integer.parseInt(maxServiceTime.getText())};
    }

    public int[] getArrivalTimeInterval() {
        return new int[]{Integer.parseInt(minArrivalTime.getText()), Integer.parseInt(maxArrivalTime.getText())};
    }

    public int getServers() {
        return Integer.parseInt(noServersField.getText());
    }

    public int getTasks() {
        return Integer.parseInt(noTasksField.getText());
    }


    public void addSimulatinListener(ActionListener a) {
        simulate.addActionListener(a);
    }

    public void setAvgServiceTimeText(String s) {
        avgServiceTimeText.setText(s);
    }

    public void setAvgWaitingTimeText(String s) {
        avgWaitingTimeText.setText(s);
    }

    public void setPeakHourText(String s) {
        peakHourText.setText(s);
    }

}
