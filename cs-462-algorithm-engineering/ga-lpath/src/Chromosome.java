


import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abuzaher
 */
public class Chromosome implements Comparable<Chromosome> {

    private ArrayList<Integer> nodeList;
    /*
    cost is the fitness measure here
    we want to maximize the fitness, everytime
     */
    private double fitnesss;
    private ArrayList<ArrayList<Double>> adjacencyMatrix;

    public Chromosome(ArrayList<ArrayList<Double>> adjacencyMatrix) {
        nodeList = new ArrayList<Integer>();
        fitnesss = 0.0;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void apppendNode(Integer node) {
        if (!nodeList.isEmpty()) {
            fitnesss += adjacencyMatrix.get(nodeList.get(nodeList.size() - 1)).get(node);
        }
        nodeList.add(node);
//        System.out.println("NODELIST " + nodeList);
//        System.out.println("FITNESS " + fitnesss);
    }

    public void removeNode(Integer node) {
//        if (!nodeList.isEmpty()){
//
//        }
        System.out.println("NOT YET IMPLEMENTED");
    }

    public Integer removeNodeByIndex(int index) {
        if (!nodeList.isEmpty()) {
            Integer nodeToBeDeleted = nodeList.get(index);
            Integer previousNode = nodeList.get(index - 1);
            double cost = adjacencyMatrix.get(previousNode).get(nodeToBeDeleted);
            fitnesss -= cost;
            nodeList.remove(index);
            return nodeToBeDeleted;
        }
        return null;
    }

    public int getLength() {
        return nodeList.size();
    }

    public Double getFitnesss() {
        return fitnesss;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ nodeList: ").append(nodeList).append(",\ncost: ").append(fitnesss).append("}\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        // we went descending order, that's why the comparison is reversed
        if (this.fitnesss < chromosome.fitnesss) {
            return 1;
        } else if (this.fitnesss > chromosome.fitnesss) {
            return -1;
        }
        return 0;
    }

    public Boolean hasNode(int node) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i) == node) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean hasCommonNodeWith(Chromosome c) {
        Boolean hasCommon = Boolean.FALSE;
        for (int i = 0; i < nodeList.size(); i++) {
            if (c.hasNode(nodeList.get(i))) {
                hasCommon = Boolean.TRUE;
                break;
            }
        }
        return hasCommon;
    }

    public Chromosome getPartialCopiedChromosome(int mutaionIndex) {
        Chromosome chromosome = new Chromosome(adjacencyMatrix);
        for (int i = 0; i < mutaionIndex; i++) {
            chromosome.apppendNode(this.nodeList.get(i));
        }
        return chromosome;
    }

    public int getNodeValueByIndex(int nodeIndex) {
        return nodeList.get(nodeIndex);
    }

    public Chromosome getFlippedChromosome() {
        // return a reversed or flipped chromosome

        Chromosome flipped = new Chromosome(adjacencyMatrix);
        for (int i = nodeList.size() - 1; i >= 0; --i) {
            flipped.apppendNode(nodeList.get(i));
        }
        return flipped;
    }

    public ArrayList<Integer> getNodeList() {
        return nodeList;
    }

    public boolean similarTo(Chromosome c) {
        // essentianlly another version of equals
        // gave different name to avoid collision

        if (this.fitnesss == c.fitnesss && this.nodeList == c.nodeList) {
            return true;
        }
        return false;
    }

    public void reverse() {
        Collections.reverse(nodeList);
    }

    public ArrayList<ArrayList<Double>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
}
