/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.util.ArrayList;

/**
 *
 * @author abuzaher
 */
public class LongestPath {

    public static void main(String args[]) {
        // set program running parameters here
        int numIterations = 100;
        int numAnts = 100;
        double initialPheromoneAmount = 10.0;
        double antPheromoneDepositAmount = 1.0;
        double daemonPheromoneDepositAmount = 1.0;
        double pheromoneEvaporationRate = 0.3;
        double maxPheromoneThreshold = 20;
        double minPheromoneThreshold = 1.0;


        for (int i = 0; i < 10; i++) {

            ArrayList<Double> inputList;
            ACOSystem acoSystem;

//        Reader.openFile("input2.txt");
//            Reader.openFile("longestPath.txt");
            Reader.openFile("input3.txt");

            inputList = Reader.readFile();
            Reader.closeFile();

            // setting all the parameters
            acoSystem = new ACOSystem(inputList);
            acoSystem.setNumIterations(numIterations);
            acoSystem.setNumAnts(numAnts);
            acoSystem.setInitialPheromoneAmount(initialPheromoneAmount);
            acoSystem.setAntPheromoneDepositAmount(antPheromoneDepositAmount);
            acoSystem.setDaemonPheromoneDepositAmount(daemonPheromoneDepositAmount);
            acoSystem.setPheromoneEvaporationRate(pheromoneEvaporationRate);
            acoSystem.setMinPheromoneThreshold(minPheromoneThreshold);
            acoSystem.setMaxPheromoneThreshold(maxPheromoneThreshold);

            acoSystem.run();

            Solution solution = acoSystem.getSolution();
            System.out.println("GLOABAL SOLUTION");
            System.out.println(solution);

        }

    }
}
