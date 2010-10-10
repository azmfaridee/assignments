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
public class Ant {

    ArrayList<Boolean> visitedStates;
    Solution solution;
    ArrayList<ArrayList<Double>> adjacencyMatrix;
    ArrayList<ArrayList<Double>> pheromoneMatrix;
    ArrayList<Integer> neighbourCount;
    int size;
    double alpha;
    double beta;
    double pheromoneDepositAmount;

    public Ant(ArrayList<ArrayList<Double>> adjacencyMatrix, ArrayList<ArrayList<Double>> pheromoneMatrix,
            double alpha, double beta, double pheromoneDepositAmount, ArrayList<Integer> neighbourCount) {

        this.adjacencyMatrix = adjacencyMatrix;
        this.pheromoneMatrix = pheromoneMatrix;
        this.solution = new Solution(adjacencyMatrix);
        this.size = adjacencyMatrix.size();
        this.alpha = alpha;
        this.beta = beta;
        this.pheromoneDepositAmount = pheromoneDepositAmount;
        this.neighbourCount = neighbourCount;

        this.visitedStates = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            visitedStates.add(Boolean.FALSE);
        }
    }

    public ArrayList<Integer> findAvailAbleNeighbours(int startIndex) {
        ArrayList<Integer> neighbours = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix.get(startIndex).get(i) > 0) {
                if (visitedStates.get(i) == false) {
                    neighbours.add(i);
                }
            }
        }
        return neighbours;
    }

    public Integer getNextNode(Integer currentNode, ArrayList<Integer> neighbours) {
        // if only one neighbour, return that one
        if (neighbours.size() == 1) {
            return neighbours.get(0);
        }

        Double sum = 0.0;
        double edgeCost;
        double pheromone;
        double probability;
        ArrayList<Double> probabilityDistribution = new ArrayList<Double>();
        int nextNodeIndex;

        for (int i = 0; i < neighbours.size(); i++) {
            edgeCost = adjacencyMatrix.get(currentNode).get(neighbours.get(i));
            pheromone = pheromoneMatrix.get(currentNode).get(neighbours.get(i));

//            sum += Math.pow(pheromone, alpha) * Math.pow(edgeCost * neighbourCount.get(neighbours.get(i)), beta);
//            sum += Math.pow(pheromone, alpha) * Math.pow(neighbourCount.get(neighbours.get(i)), beta);
            sum += Math.pow(pheromone, alpha) * Math.pow(edgeCost, beta);
        }

        for (int i = 0; i < neighbours.size(); i++) {
            edgeCost = adjacencyMatrix.get(currentNode).get(neighbours.get(i));
            pheromone = pheromoneMatrix.get(currentNode).get(neighbours.get(i));

//            probability = Math.pow(pheromone, alpha) * Math.pow(edgeCost * neighbourCount.get(neighbours.get(i)), beta) / sum;
//            probability = Math.pow(pheromone, alpha) * Math.pow(neighbourCount.get(neighbours.get(i)), beta) / sum;
            probability = Math.pow(pheromone, alpha) * Math.pow(edgeCost, beta) / sum;

            probabilityDistribution.add(probability);
        }

        nextNodeIndex = Utility.getRandomNodeIndex(probabilityDistribution);
        return neighbours.get(nextNodeIndex);
    }

    public void updateVisited() {
        // first parameter is the index of the current node
        for (int i = 0; i < solution.getSize(); i++) {
            visitedStates.set(solution.getNodeByIndex(i), Boolean.TRUE);
        }
    }

    public void findBetterSolution(Integer tabuNode) {
//        System.out.println("PARTIAL SOLUTION " + solution);
//        System.out.println("VISITED " + visitedStates);
//        System.out.println("TABU NODE " + tabuNode);

        Integer currentNode = solution.getNodeByIndex(solution.getSize() - 1);

//        System.out.println("CURRENT " + currentNode);

        ArrayList possibleNeighbours = findAvailAbleNeighbours(currentNode);

//        System.out.println("NEIGHBOURS " + possibleNeighbours);
        // there will be at least one node
//        possibleNeighbours.remove(possibleNeighbours.indexOf(tabuNode));

        if (possibleNeighbours.isEmpty()) {
            return;
        }

        int nextNode = getNextNode(currentNode, possibleNeighbours);
        findSolution(nextNode, false);
//        System.out.println("NEW SOLUTION " + solution);
    }

    public void findSolution(Integer currentNode, boolean firstCall) {

        // mark this node as visited
        visitedStates.set(currentNode, Boolean.TRUE);
        solution.appendNode(currentNode);

        // get this nodes neighbours who are not visited
        ArrayList<Integer> possibleNeighbours = findAvailAbleNeighbours(currentNode);

        if (possibleNeighbours.isEmpty()) {
            return;
        }
        int nextNode = getNextNode(currentNode, possibleNeighbours);
        findSolution(nextNode, false);

        // a simple trick to optimize the solution quality
        // if this is the first call, then check if there is another valid way from the
        // first node, so we can merge the two paths and get a bigger path
        if (firstCall) {
//            System.out.println("PARTIAL SOLUTION " + solution);
            possibleNeighbours = findAvailAbleNeighbours(currentNode);
            if (possibleNeighbours.isEmpty()) {
//                System.out.println("LEAVING PREM");
                return;
            }
//            System.out.println("REMAINGING NEIGHBOURS " + possibleNeighbours );
            nextNode = getNextNode(currentNode, possibleNeighbours);
//            System.out.println("BACK PASS NEXT NODE " + nextNodeIndex);
            solution.reverseOrder();
            findSolution(nextNode, false);
            solution.reverseOrder();
//            System.out.println("FULL SOLUTION " + solution);
        }
    }

    public void depositPheromone(double maxPheromoneThreshold) {
        double previousValue;
        double newValue;

        for (int i = 0; i < solution.getSize() - 1; i++) {
            previousValue = pheromoneMatrix.get(i).get(i + 1);
            newValue = previousValue + pheromoneDepositAmount;
            if (newValue < maxPheromoneThreshold) {
                pheromoneMatrix.get(i).set(i + 1, newValue);
            }
        }
    }

    Solution getSolution() {
        return this.solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
