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

    public void appendNode(SlicingTreeNode node){
        this.nodeList.add(node);
    }

    public ArrayList<SlicingTreeNode> getNodeList() {
        return nodeList;
    }

    

}
