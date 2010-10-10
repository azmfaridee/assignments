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

    public void copy(Solution source) {
        this.nodeList = source.nodeList;
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

    Solution getPartialCopy(int index) {
        Solution partialSolution = new Solution(adjacencyMatrix);
        for (int i = 0; i < index; i++) {
            partialSolution.appendNode(nodeList.get(i));
        }
        return partialSolution;
    }

    Solution getReversedCopy(){
        Solution reversedSolution = new Solution(adjacencyMatrix);
        reversedSolution.nodeList.addAll(this.nodeList);
        reversedSolution.cost = this.cost;
        Collections.reverse(reversedSolution.nodeList);
        return  reversedSolution;
    }

    Integer getNodeByIndex(int index){
        return nodeList.get(index);
    }
}
