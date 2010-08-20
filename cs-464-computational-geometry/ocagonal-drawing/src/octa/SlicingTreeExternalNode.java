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
public class SlicingTreeExternalNode implements SlicingTreeNode {

    private int faceArea;
    private int parentId;
    private int id;
    private ArrayList<Integer> clockwiseMemberVertices;
    private int northWestVertex;
    private int northEastVertex;
    private int southEastVertex;
    private int southWestVertex;
    private boolean isRightChild;
    private SlicingTreeNode parent;

    public SlicingTreeExternalNode(int id) {
        this.id = id;
        northEastVertex = northWestVertex = southEastVertex = southWestVertex = 0;
        isRightChild = false;
        parent = null;
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
