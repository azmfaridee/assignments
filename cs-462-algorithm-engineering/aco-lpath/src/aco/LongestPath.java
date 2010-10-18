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

    public static void main(String args[]) throws InterruptedException {
        // set program running parameters here
        int numIterations = 10;
        int numAnts = 100;
        double initialPheromoneAmount = 20.0;
        double antPheromoneDepositAmount = 1.0;
        double daemonPheromoneDepositAmount = 10.0;
        double pheromoneEvaporationRate = 0.3;
        double maxPheromoneThreshold = 100.0;
        double minPheromoneThreshold = 0.001;
        double alpha = 3.0;
        double beta = 2.0;
        // the less the exploitation factor, the more probabilistic the nature
        double exploitationFactor = 0.25;


        for (int i = 0; i < 10; i++) {

            ArrayList<Double> inputList;
            ACOSystem acoSystem;

//        Reader.openFile("input2.txt");
//            Reader.openFile("longestPath.txt");
//            Reader.openFile("input3.txt");
//            Reader.openFile("input4.txt");
//            Reader.openFile("input5.txt");
            Reader.openFile("input6.txt");

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
            acoSystem.setAlpha(alpha);
            acoSystem.setBeta(beta);
            acoSystem.setExploitationFactor(exploitationFactor);

//            acoSystem.initializePheromoneMatrix();
            acoSystem.run();

            Solution solution = acoSystem.getSolution();
            System.out.println("GLOABAL SOLUTION");
            System.out.println(solution);
//            acoSystem.printPheromoneMatrix();

        }

    }
}
