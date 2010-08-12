/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.util.ArrayList;

/**
 *
 * @author abuzaher
 */
public class SlicingTree {

    ArrayList<SlicingTreeNode> nodeList;
    int numInternalNodes;
    int numExternalNodes;

    public SlicingTree() {
        this.nodeList = new ArrayList<SlicingTreeNode>();
    }

    public void setNumInternalNodes(int numInternalNodes) {
        this.numInternalNodes = numInternalNodes;
    }

    public void setNumExternalNodes(int numExternalNodes) {
        this.numExternalNodes = numExternalNodes;
    }

    public void appendNode(SlicingTreeNode node) {
        this.nodeList.add(node);
    }

    public ArrayList<SlicingTreeNode> getNodeList() {
        return nodeList;
    }

    public void printInfo() {
        System.out.println("Number of internal nodes (cuts): " + this.numInternalNodes);

        for (int i = 0; i < this.numInternalNodes; i++) {
            SlicingTreeInternalNode node = (SlicingTreeInternalNode) this.nodeList.get(i);
            System.out.println("Parent of inernal node (cut)" + node.getId() + ": " + node.getParentId());
            System.out.println("Node Type: " + node.getCutType());
            System.out.println("Slicing path for this node: " + node.getSlicingPath());
        }

        System.out.println("Number of external nodes (faces): " + this.numExternalNodes);

        for (int i = this.numInternalNodes; i < nodeList.size(); i++) {
            SlicingTreeExternalNode node = (SlicingTreeExternalNode) this.nodeList.get(i);
            System.out.println("Parent of leaf node (face) " + node.getId() + ": " + node.getParentId());
            System.out.println("Area under face: " + node.getId() + ": " + node.getFaceArea());
        }
    }
}
