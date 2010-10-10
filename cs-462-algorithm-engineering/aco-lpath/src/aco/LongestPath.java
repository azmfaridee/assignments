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
        double antPheromoneDepositAmount = 0.0;
        double daemonPheromoneDepositAmount = 1.0;
        double pheromoneEvaporationRate = 0.4;


        for (int i = 0; i < 10; i++) {

            ArrayList<Double> inputList;
            ACOSystem acoSystem;

//        Reader.openFile("input2.txt");
//            Reader.openFile("longestPath.txt");
            Reader.openFile("input3.txt");

            inputList = Reader.readFile();
            Reader.closeFile();

            acoSystem = new ACOSystem(inputList);
            acoSystem.setNumIterations(numIterations);
            acoSystem.setNumAnts(numAnts);
            acoSystem.setInitialPheromoneAmount(initialPheromoneAmount);
            acoSystem.setAntPheromoneDepositAmount(antPheromoneDepositAmount);
            acoSystem.setDaemonPheromoneDepositAmount(daemonPheromoneDepositAmount);
            acoSystem.setPheromoneEvaporationRate(pheromoneEvaporationRate);

            acoSystem.run();

            Solution solution = acoSystem.getSolution();
            System.out.println("GLOABAL SOLUTION");
            System.out.println(solution);

        }

    }
}
