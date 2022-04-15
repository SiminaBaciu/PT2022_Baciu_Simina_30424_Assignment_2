package controller;

import controller.Utilities;
import GUI.SimulationFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SimulationFrame gui = new SimulationFrame();
        gui.setTitle("Queue Manager");
        gui.setVisible(true);
        gui.setBounds(10, 10, 750, 750);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("C:\\PT\\PT2022_Baciu_Simina_30424_Assignment_2\\download.png");
        gui.setIconImage(image);
        Utilities controller = new Utilities(gui);

    }
}


