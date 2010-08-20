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
    private ArrayList<Integer> clockwiseMemberVertices;
    private int northWestVertex;
    private int northEastVertex;
    private int southEastVertex;
    private int southWestVertex;
    private boolean isRightChild;
    private SlicingTreeNode parent;

    public SlicingTreeInternalNode(int id) {
        this.id = id;
        this.rightChild = null;
        this.leftChild = null;
        // set the initial area to zero
        this.faceArea = 0;
        northEastVertex = northWestVertex = southEastVertex = southWestVertex = 0;
        parent = null;
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

    /**
     * @return the clockwiseMemberVertices
     */
    public ArrayList<Integer> getClockwiseMemberVertices() {
        return clockwiseMemberVertices;
    }

    /**
     * @param clockwiseMemberVertices the clockwiseMemberVertices to set
     */
    public void setClockwiseMemberVertices(ArrayList<Integer> clockwiseMemberVertices) {
        this.clockwiseMemberVertices = clockwiseMemberVertices;
    }

    public void pruneInteriorVertices() {
        for (int i = 1; i < this.slicingPath.size() - 1; i++) {
            Integer innerVertex = this.slicingPath.get(i);
            int index = this.getClockwiseMemberVertices().indexOf(innerVertex);
            if (index != -1) {
                // we have found an innter vertex which is invalid
                this.getClockwiseMemberVertices().remove(innerVertex);
            }
        }
    }

    /**
     * @return the northWestVertex
     */
    public int getNorthWestVertex() {
        return northWestVertex;
    }

    /**
     * @param northWestVertex the northWestVertex to set
     */
    public void setNorthWestVertex(int northWestVertex) {
        this.northWestVertex = northWestVertex;
    }

    /**
     * @return the northEastVertex
     */
    public int getNorthEastVertex() {
        return northEastVertex;
    }

    /**
     * @param northEastVertex the northEastVertex to set
     */
    public void setNorthEastVertex(int northEastVertex) {
        this.northEastVertex = northEastVertex;
    }

    /**
     * @return the southEastVertex
     */
    public int getSouthEastVertex() {
        return southEastVertex;
    }

    /**
     * @param southEastVertex the southEastVertex to set
     */
    public void setSouthEastVertex(int southEastVertex) {
        this.southEastVertex = southEastVertex;
    }

    /**
     * @return the southWestVertex
     */
    public int getSouthWestVertex() {
        return southWestVertex;
    }

    /**
     * @param southWestVertex the southWestVertex to set
     */
    public void setSouthWestVertex(int southWestVertex) {
        this.southWestVertex = southWestVertex;
    }

    public void setCornetVertices(ArrayList<Integer> cornetVertices) {
        northEastVertex = cornetVertices.get(0);
        southEastVertex = cornetVertices.get(1);
        southWestVertex = cornetVertices.get(2);
        northWestVertex = cornetVertices.get(3);
    }

    public ArrayList<Integer> getCornetVertices() {
        ArrayList<Integer> cornerVertices = new ArrayList<Integer>();
        cornerVertices.add(northEastVertex);
        cornerVertices.add(southEastVertex);
        cornerVertices.add(southWestVertex);
        cornerVertices.add(northWestVertex);
        return cornerVertices;
    }

    public boolean isIsRightChild() {
        return isRightChild;
    }

    public void setIsRightChild(boolean isRightChild) {
        this.isRightChild = isRightChild;
    }

    public void setParent(SlicingTreeNode parent) {
        this.parent = parent;
    }

    public SlicingTreeNode getParent() {
        return parent;
    }

    
    
}
