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
public class SlicingTreeInternalNode implements SlicingTreeNode {

    private CutType cutType;
    private int id;
    private int parentId;
    private SlicingTreeNode rightChild;
    private SlicingTreeNode leftChild;
    private ArrayList<Integer> slicingPath;
    private int faceArea;

    public SlicingTreeInternalNode(int id) {
        this.id = id;
        this.rightChild = null;
        this.leftChild = null;
        // set the initial area to zero
        this.faceArea = 0;
    }

    public void setCutType(CutType cutType) {
        this.cutType = cutType;
    }

    public void setSlicingPath(ArrayList<Integer> slicingPath) {
        this.slicingPath = slicingPath;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setRightChild(SlicingTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(SlicingTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public CutType getCutType() {
        return cutType;
    }

    public ArrayList<Integer> getSlicingPath() {
        return slicingPath;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getId() {
        return id;
    }

    public SlicingTreeNode getLeftChild() {
        return leftChild;
    }

    public SlicingTreeNode getRightChild() {
        return rightChild;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFaceArea() {
        return this.faceArea;
    }

    public void setFaceArea(int faceArea) {
        this.faceArea = faceArea;
    }
}
