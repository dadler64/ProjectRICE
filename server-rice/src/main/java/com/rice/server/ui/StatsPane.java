package com.rice.server.ui;

import com.sun.management.OperatingSystemMXBean;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

import java.lang.management.ManagementFactory;

public class StatsPane extends TitledPane implements Runnable {

    private final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private final GridPane statsGrid = new GridPane();
    private final Label cpu = new Label();
    private final ProgressBar cpuLoad = new ProgressBar(0.0);
    private int updateSpeed = 5;

    public StatsPane() {
        initGUI();
    }

    public void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }


    private void initGUI() {
        this.cpu.setText("CPU");
        this.cpu.setAlignment(Pos.CENTER);

        this.statsGrid.add(cpu, 0, 0);
        this.statsGrid.add(cpuLoad, 1, 0);
        this.statsGrid.setHgap(5);
        this.statsGrid.setVgap(10);

        this.setCollapsible(false);
        this.setText("Stats");
        this.setContent(statsGrid);

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                double load = os.getProcessCpuLoad();
                cpuLoad.setProgress(load);
                System.out.printf("CPU Load: %.1f%n", load);
                Thread.sleep(updateSpeed * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
