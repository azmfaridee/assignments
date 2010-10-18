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
    private int totalVertices;
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
    private double exploitationFactor;

    ACOSystem(ArrayList<Double> inputList) {
        initializeMatrixes(inputList);

//        initializePheromoneMatrix();

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
        totalVertices = (int) Math.sqrt(inputList.size() - 2);
        adjacencyMatrix = new ArrayList<ArrayList<Double>>();
        pheromoneMatrix = new ArrayList<ArrayList<Double>>();
        neighbourCount = new ArrayList<Integer>();
        ants = new ArrayList<Ant>();

        for (int i = 0; i < totalVertices; i++) {
            adjacencyMatrix.add(new ArrayList<Double>());
            pheromoneMatrix.add(new ArrayList<Double>());
        }

        // read adj data from imput
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                adjacencyMatrix.get(i).add(inputList.get(i * totalVertices + j));
                pheromoneMatrix.get(i).add(0.0);
            }
        }

        // calculate the number of neighbours for each node
        for (int i = 0; i < totalVertices; i++) {
            neighbourCount.add(0);
            for (int j = 0; j < totalVertices; j++) {
                if (adjacencyMatrix.get(i).get(j) > 0) {
                    neighbourCount.set(i, neighbourCount.get(i) + 1);
                }
            }
        }

    }

    public void initializePheromoneMatrix() {
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                if (adjacencyMatrix.get(i).get(j) > 0) {
                    pheromoneMatrix.get(i).set(j, initialPheromoneAmount);
                } else {
                    pheromoneMatrix.get(i).set(j, 0.0);
                }
            }
        }
    }

    void run() throws InterruptedException {
        initializePheromoneMatrix();
//        System.out.println(neighbourCount);
//        printAdjMatrix();
//        printPheromoneMatrix();

        for (int i = 0; i < numIterations; i++) {
//            System.out.println("STARTING ITERATION: " + i);
            Solution localBestSolution = new Solution(adjacencyMatrix);

            // if initialized, each run becomes independent
//            initializePheromoneMatrix();
            for (int j = 0; j < numAnts; j++) {
                Ant ant = new Ant(adjacencyMatrix, pheromoneMatrix, alpha, beta, antPheromoneDepositAmount, neighbourCount, exploitationFactor);

                // TODO: need to carefully randomize the intial ant start node
                int startNode = random.nextInt(totalVertices);
                ant.findSolution(startNode, true);
                ant.depositPheromone(maxPheromoneThreshold);
                Solution solution = ant.getSolution();

//                System.out.println("ITERATION: " + i + ", ANT: " + j);
//                System.out.println(solution);
//                System.out.println("PHEROMONE MATRIX: " + pheromoneMatrix);
//                System.out.println();

                if (solution.getCost() > localBestSolution.getCost()) {
                    localBestSolution = solution;
                }

                // check if there is a way to improve this solution
//                System.out.println("LOCAL SEARCH");
//                System.out.println(localBestSolution);
                localBestSolution = randomLocalSearch(localBestSolution);
//                System.out.println("AFTER LOCAL SEARCH");
//                System.out.println(localBestSolution);
                localBestSolution = randomLocalSearch(localBestSolution.getReversedCopy());
//                System.out.println(localBestSolution);
//                System.out.println("");

                localBestSolution = randomLocalSearch2(localBestSolution);

                checkHamiltonianCycle(localBestSolution);

                globalPheromoneUpdate(localBestSolution);

//                printPheromoneMatrix();
//                Thread.sleep(500);
            }
            if (localBestSolution.getCost() > globalBestSolution.getCost()) {
                globalBestSolution = localBestSolution;
            }

        }
//        System.out.println("LAST TRY, MAN");
//        initializePheromoneMatrix();
//        for (int i = 0; i < 5; i++) {
//            globalBestSolution = randomLocalSearch(globalBestSolution);
//            globalBestSolution = randomLocalSearch(globalBestSolution.getReversedCopy());
//        }

    }

    private void globalPheromoneUpdate(Solution localBestSolution) {
        // evaporation
        for (int i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                if (i != j) {
                    double value = pheromoneMatrix.get(i).get(j);
                    value = value * (1 - pheromoneEvaporationRate);
                    if (value > minPheromoneThreshold) {
                        pheromoneMatrix.get(i).set(j, value);
                    }
                }
            }
        }

        // more pheromone deposit in the best solution
//        System.out.println(localBestSolution);
        for (int i = 0; i < localBestSolution.getSize() - 1; i++) {
            double value1 = pheromoneMatrix.get(i).get(i + 1);
            double value2 = pheromoneMatrix.get(i + 1).get(i);
            value1 += daemonPheromoneDepositAmount;
            value2 += daemonPheromoneDepositAmount;
            if (value1 < maxPheromoneThreshold) {
                pheromoneMatrix.get(localBestSolution.getNodeByIndex(i)).set(localBestSolution.getNodeByIndex(i + 1), value1);
            }
            if (value2 < maxPheromoneThreshold) {
                pheromoneMatrix.get(localBestSolution.getNodeByIndex(i + 1)).set(localBestSolution.getNodeByIndex(i), value2);
            }
        }
    }

    private Solution randomLocalSearch(Solution localBestSolution) {
        int localBestSolutionSize = localBestSolution.getSize();

        // random point must be one less that the last index, so that there
        // is one more point that WILL NOT be followed
//        System.out.println("LOCAL BEST " + localBestSolution);
//        int randomPointIndex = random.nextInt(localBestSolution.getSize() - 2) + 1;
//        int randomPointIndex = random.nextInt(localBestSolution.getSize() / 2) + localBestSolution.getSize() / 2 - 1;

        if (localBestSolutionSize < 3) {
            System.out.println("TOO SHORT: " + localBestSolution);
            return localBestSolution;
        }

        ArrayList<Integer> threeNeighbourVertices = localBestSolution.getThreeNeighbourVerticesFromHalfToEnd();
        if (threeNeighbourVertices.isEmpty()) {
            return localBestSolution;
        }

        Integer targetNode = threeNeighbourVertices.get(random.nextInt(threeNeighbourVertices.size()));
//        System.out.println(targetNode);
//        System.out.println(localBestSolution);
//        System.out.println(localBestSolution.getNodeList().indexOf(targetNode));


        // partial copy size must be one less than the parents size
//        int partialCopySize = (localBestSolution.getSize() / 2) + random.nextInt(localBestSolution.getSize() / 2);
        int partialCopySize = localBestSolution.getNodeList().indexOf(targetNode) + 1;
//        System.out.println(partialCopySize);

        Solution partialSolution = localBestSolution.getPartialCopy(partialCopySize);
//        System.out.println(partialSolution);
        Integer tabuNode = localBestSolution.getNodeByIndex(partialCopySize);

        Ant wiseAnt = new Ant(adjacencyMatrix, pheromoneMatrix, alpha, beta, antPheromoneDepositAmount, neighbourCount, exploitationFactor);
        wiseAnt.setSolution(partialSolution);
        wiseAnt.updateVisited();
        wiseAnt.findBetterSolution(tabuNode);

        if (wiseAnt.getSolution().getCost() > localBestSolution.getCost()) {
//            System.out.println("LOCAL SEARCH SUCCESS: " + localBestSolution.getCost() + "->" + wiseAnt.getSolution().getCost());
//            System.out.println("PREVIOUS BEST " + localBestSolution);
//            System.out.println("TABU NODE " + tabuNode + " AT INDEX " + partialCopySize);
//            System.out.println("NEW BEST " + wiseAnt.getSolution());
            localBestSolution = wiseAnt.getSolution();
        } else {
//            System.out.println("NO IMPROVEMENT " + wiseAnt.getSolution());
        }
        return localBestSolution;
    }

    private void checkHamiltonianCycle(Solution localBestSolution) {
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

    public void setMaxPheromoneThreshold(double maxPheromoneThreshold) {
        this.maxPheromoneThreshold = maxPheromoneThreshold;
    }

    public void setMinPheromoneThreshold(double minPheromoneThreshold) {
        this.minPheromoneThreshold = minPheromoneThreshold;
    }

    public ArrayList<ArrayList<Double>> getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    public void printPheromoneMatrix() {
        System.out.println("PH MAT");
//        System.out.println("[");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print("[");
            for (int j = 0; j < totalVertices; j++) {
                System.out.printf("%.1f", pheromoneMatrix.get(i).get(j));
                if (j < totalVertices - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("]");
            System.out.println();
        }
//        System.out.print("]");
    }

    public void printAdjMatrix() {
        System.out.println("ADJ MAT");
//        System.out.println("[");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print("[");
            for (int j = 0; j < totalVertices; j++) {
                System.out.printf("%.1f", adjacencyMatrix.get(i).get(j));
                if (j < totalVertices - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("]");
            System.out.println();
        }
//        System.out.print("]");
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    private Solution randomLocalSearch2(Solution localBestSolution) {

        // TODO: right now we are taking the first pair that we find and break
        // but there might be better pairs in later stages of the iteration
        // need to check that too

        Solution improvedSolution = new Solution(adjacencyMatrix);
        improvedSolution.copyFrom(localBestSolution);

//        System.out.println(improvedSolution);

        for (int i = 0; i < totalVertices; i++) {
            if (!improvedSolution.hasVertex(i)) {
//                System.out.println("NOT VISITED: " + i);
                // we have a non visited vertex
                int j = 0;
                while (true) {
                    if (j >= improvedSolution.getSize() - 1) {
                        break;
                    }
                    Integer currentVertex = improvedSolution.getNodeByIndex(j);
                    Integer nextVertex = improvedSolution.getNodeByIndex(j + 1);
//                    if (i == 64){
//                        System.out.println(currentVertex + " "  + i  + " " + nextVertex);
//                        System.out.print(adjacencyMatrix.get(currentVertex).get(i) + " ");
//                        System.out.println(adjacencyMatrix.get(i).get(nextVertex));
//                    }

                    if (adjacencyMatrix.get(currentVertex).get(i) > 0 && adjacencyMatrix.get(i).get(nextVertex) > 0) {
                        double prevCost = adjacencyMatrix.get(currentVertex).get(nextVertex);
                        double newCost = adjacencyMatrix.get(currentVertex).get(i) + adjacencyMatrix.get(i).get(nextVertex);
                        if (newCost >= prevCost) {
                            improvedSolution.insertAfterVertex(currentVertex, i);
                            break;
                        }
                    }
                    j++;
                }
            }
        }
//        if (improvedSolution.getCost() > localBestSolution.getCost()) {
//            System.out.println("COOL\n" + localBestSolution + "\n=>\n" + improvedSolution);
//        }
        return improvedSolution;
    }

    public void setExploitationFactor(double exploitationFactor) {
        this.exploitationFactor = exploitationFactor;
    }

    public double getExploitationFactor() {
        return exploitationFactor;
    }
}
