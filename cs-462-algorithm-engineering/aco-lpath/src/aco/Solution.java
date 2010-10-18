/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author abuzaher
 */
public class Solution {

    private ArrayList<Integer> nodeList;
    private Double cost;
    private ArrayList<ArrayList<Double>> adjacencyMatrix;

    public Solution(ArrayList<ArrayList<Double>> adjacencyMatrix) {
        this.nodeList = new ArrayList<Integer>();
        this.cost = 0.0;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void appendNode(Integer nodeIndex) {
        if (!nodeList.isEmpty()) {
            cost += adjacencyMatrix.get(nodeList.get(nodeList.size() - 1)).get(nodeIndex);
        }
        nodeList.add(nodeIndex);
    }

    public void copyFrom(Solution source) {
        this.nodeList.clear();
        this.nodeList.addAll(source.nodeList);
        this.cost = source.cost;
    }

    public int getSize() {
        return nodeList.size();
    }

    public Double getCost() {
        return cost;
    }

    public ArrayList<Integer> getNodeList() {
        return nodeList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solution: ").append(nodeList).append(", Cost: ").append(cost);
        return sb.toString();
    }

    public void reverseOrder() {
        Collections.reverse(nodeList);
    }

    public ArrayList<ArrayList<Double>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    Solution getPartialCopy(int size) {
        // copies the solution upto (exclusive) a specific index
        // alternatively, creates a partial copy of given size
        Solution partialSolution = new Solution(adjacencyMatrix);
        for (int i = 0; i < size; i++) {
            partialSolution.appendNode(nodeList.get(i));
        }
        return partialSolution;
    }

    Solution getReversedCopy() {
        Solution reversedSolution = new Solution(adjacencyMatrix);
        reversedSolution.nodeList.addAll(this.nodeList);
        reversedSolution.cost = this.cost;
        Collections.reverse(reversedSolution.nodeList);
        return reversedSolution;
    }

    Integer getNodeByIndex(int index) {
        return nodeList.get(index);
    }

    boolean hasVertex(Integer vertex) {
        if (nodeList.indexOf(vertex) == -1) {
            return false;
        }
        return true;
    }

    void insertAfterVertex(Integer prevVertex, Integer vertexToInsert) {
        int index = nodeList.indexOf(prevVertex) + 1;
        // we'll insert the new vertex at the place of nextVertex, and it will be shifted one place
        Integer nextVertex = nodeList.get(index);
        nodeList.add(index, vertexToInsert);

        double extraCost = adjacencyMatrix.get(prevVertex).get(vertexToInsert) + adjacencyMatrix.get(vertexToInsert).get(nextVertex);
        double costToRemove = adjacencyMatrix.get(prevVertex).get(nextVertex);
        cost = cost + extraCost - costToRemove;
    }

    ArrayList<Integer> getThreeNeighbourVerticesFromHalfToEnd() {
        ArrayList<Integer> threeNeighbourVertices = new ArrayList<Integer>();
        int startIndex = getSize() / 2 - 1;
        int endIndex = getSize() - 2;

        for (int i = startIndex; i <= endIndex; i++) {
            int neighbours = 0;
            for (int j = 0; j < adjacencyMatrix.size(); j++) {
                if (adjacencyMatrix.get(nodeList.get(i)).get(j) > 0) {
//                    if (nodeList.indexOf(j) > i) {
////                        System.out.println("ASDS");
////                        System.out.println(nodeList.indexOf(j) + " " + j);
////                        System.out.println(i + " " + nodeList.get(i));
//                    }
                    neighbours++;
                }
            }
            if (neighbours > 2) {
                threeNeighbourVertices.add(nodeList.get(i));
            }
        }
        return threeNeighbourVertices;
    }
}
