/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author abuzaher
 */
public class ACOSystem {

    private ArrayList<ArrayList<Double>> adjacencyMatrix;
    private ArrayList<ArrayList<Double>> pheromoneMatrix;
    private ArrayList<Integer> neighbourCount;
    private double alpha;
    private double beta;
    private int size;
    private double initialPheromoneAmount = 5.0;
    private double antPheromoneDepositAmount = 2.0;
    // setting off daemonPheromoneDepositAmount seems to improve the performance
    private double daemonPheromoneDepositAmount = 2.0;
    private double pheromoneEvaporationRate = 0.4;
    private double minPheromoneThreshold = 1.0;
    private double maxPheromoneThreshold = 20.0;
    private Solution globalBestSolution;
    private int numIterations = 100;
    private int numAnts = 100;
    private ArrayList<Ant> ants;
    private Random random;

    ACOSystem(ArrayList<Double> inputList) {
        initializeMatrixes(inputList);

        initializePheromoneMatrix();

        globalBestSolution = new Solution(adjacencyMatrix);
        random = new Random(new Date().getTime());


//        System.out.println(adjecencyMatrix);
//        System.out.println(pheromoneMatrix);
//        System.out.println(alpha);
//        System.out.println(beta);
    }

    private void initializeMatrixes(ArrayList<Double> inputList) {
        alpha = inputList.get(inputList.size() - 2);
        beta = inputList.get(inputList.size() - 1);
        size = (int) Math.sqrt(inputList.size() - 2);
        adjacencyMatrix = new ArrayList<ArrayList<Double>>();
        pheromoneMatrix = new ArrayList<ArrayList<Double>>();
        neighbourCount = new ArrayList<Integer>();
        ants = new ArrayList<Ant>();

        for (int i = 0; i < size; i++) {
            adjacencyMatrix.add(new ArrayList<Double>());
            pheromoneMatrix.add(new ArrayList<Double>());
        }

        // read adj data from imput
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix.get(i).add(inputList.get(i * size + j));
                pheromoneMatrix.get(i).add(0.0);
            }
        }

        // calculate the number of neighbours for each node
        for (int i = 0; i < size; i++) {
            neighbourCount.add(0);
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix.get(i).get(j) > 0) {
                    neighbourCount.set(i, neighbourCount.get(i) + 1);
                }
            }
        }

    }

    private void initializePheromoneMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix.get(i).get(j) > 0) {
                    pheromoneMatrix.get(i).set(j, initialPheromoneAmount);
                } else {
                    pheromoneMatrix.get(i).set(j, 0.0);
                }
            }
        }
    }

    void run() {
//        System.out.println(neighbourCount);

        for (int i = 0; i < numIterations; i++) {
//            System.out.println("STARTING ITERATION: " + i);
            Solution localBestSolution = new Solution(adjacencyMatrix);

            // if initialized, each run becomes independent
//            initializePheromoneMatrix();
            for (int j = 0; j < numAnts; j++) {
                Ant ant = new Ant(adjacencyMatrix, pheromoneMatrix, alpha, beta, antPheromoneDepositAmount, neighbourCount);

                // TODO: need to carefully randomize the intial ant start node
                int startNode = random.nextInt(size);
                ant.findSolution(startNode, true);
                ant.depositPheromone(maxPheromoneThreshold);
                Solution solution = ant.getSolution();

//                System.out.println("ITERATION: " +  i + ", ANT: " + j );
//                System.out.println(solution);
//                System.out.println("PHEROMONE MATRIX: " + pheromoneMatrix);
//                System.out.println();

                if (solution.getCost() > localBestSolution.getCost()) {
                    localBestSolution = solution;
                }
                // check if there is a way to improve this solution
//                System.out.println(localBestSolution);
                localBestSolution = randomLocalSearch(localBestSolution);
//                System.out.println(localBestSolution);
                localBestSolution = randomLocalSearch(localBestSolution.getReversedCopy());
//                System.out.println(localBestSolution);
//                System.out.println("");
                checkHamiltonianCycle(localBestSolution);
                globalPheromoneUpdate(localBestSolution);
            }
            if (localBestSolution.getCost() > globalBestSolution.getCost()) {
                globalBestSolution = localBestSolution;
            }

        }
    }

    private void globalPheromoneUpdate(Solution localBestSolution) {
        // evaporation
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double value = pheromoneMatrix.get(i).get(j);
                value = value * (1 - pheromoneEvaporationRate);
                if (value > minPheromoneThreshold) {
                    pheromoneMatrix.get(i).set(j, value);
                }
            }
        }
        // more pheromone deposit in the best solution
        for (int i = 0; i < localBestSolution.getSize() - 1; i++) {
            double value = pheromoneMatrix.get(i).get(i + 1);
            value = value + daemonPheromoneDepositAmount;
            if (value < maxPheromoneThreshold) {
                pheromoneMatrix.get(i).set(i + 1, value);
            } else {
//                System.out.println("EXCEED");
            }
        }
    }

    private Solution randomLocalSearch(Solution localBestSolution) {
        // random point must be one less that the last index, so that there
        // is one more point that WILL NOT be followed
//        System.out.println("LOCAL BEST" + localBestSolution);
//        int randomPointIndex = random.nextInt(localBestSolution.getSize() - 2) + 1;
        int randomPointIndex = random.nextInt(localBestSolution.getSize() / 2) + localBestSolution.getSize() / 2 - 1;

        Solution partialSolution = localBestSolution.getPartialCopy(randomPointIndex);
        Integer tabuNode = localBestSolution.getNodeByIndex(randomPointIndex);

        Ant wiseAnt = new Ant(adjacencyMatrix, pheromoneMatrix, alpha, beta, antPheromoneDepositAmount, neighbourCount);
        wiseAnt.setSolution(partialSolution);
        wiseAnt.updateVisited();
        wiseAnt.findBetterSolution(tabuNode);

        if (wiseAnt.getSolution().getCost() > localBestSolution.getCost()) {
//            System.out.println("LOCAL SEARCH SUCCESS: " + localBestSolution.getCost() + "->" + wiseAnt.getSolution().getCost());
            localBestSolution = wiseAnt.getSolution();
        }
        return localBestSolution;
    }

    Solution getSolution() {
        return globalBestSolution;
    }

    public void setInitialPheromoneAmount(double initialPheromoneAmount) {
        this.initialPheromoneAmount = initialPheromoneAmount;
    }

    public void setAntPheromoneDepositAmount(double antPheromoneDepositAmount) {
        this.antPheromoneDepositAmount = antPheromoneDepositAmount;
    }

    public void setDaemonPheromoneDepositAmount(double daemonPheromoneDepositAmount) {
        this.daemonPheromoneDepositAmount = daemonPheromoneDepositAmount;
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public void setPheromoneEvaporationRate(double pheromoneEvaporationRate) {
        this.pheromoneEvaporationRate = pheromoneEvaporationRate;
    }

    public void setNumAnts(int numAnts) {
        this.numAnts = numAnts;
    }

    private void checkHamiltonianCycle(Solution localBestSolution) {
    }

    public void setMaxPheromoneThreshold(double maxPheromoneThreshold) {
        this.maxPheromoneThreshold = maxPheromoneThreshold;
    }

    public void setMinPheromoneThreshold(double minPheromoneThreshold) {
        this.minPheromoneThreshold = minPheromoneThreshold;
    }
}
