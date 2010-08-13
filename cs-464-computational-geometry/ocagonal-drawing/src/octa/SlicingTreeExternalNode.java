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
public class SlicingTreeExternalNode implements SlicingTreeNode{
    private int faceArea;
    private int parentId;
    private int id;
    private ArrayList<Integer> clockwiseMemberVertices;

    public SlicingTreeExternalNode(int id) {
        this.id = id;
    }

    public void setFaceArea(int faceArea) {
        this.faceArea = faceArea;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getFaceArea() {
        return this.faceArea;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getClockwiseMemberVertices() {
        return clockwiseMemberVertices;
    }

    public void setClockwiseMemberVertices(ArrayList<Integer> clockwiseMemberVertices) {
        this.clockwiseMemberVertices = clockwiseMemberVertices;
    }

}
