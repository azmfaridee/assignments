/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa.old;

import java.util.ArrayList;

/**
 *
 * @author abuzaher
 */
public interface SlicingTreeNode {

    public int getParentId();

    public int getId();

    public int getFaceArea();

    public void setParentId(int parentId);

    public void setFaceArea(int faceArea);

    public ArrayList<Integer> getClockwiseMemberVertices();

    public void setClockwiseMemberVertices(ArrayList<Integer> clockwiseMemberVertices);

    public int getNorthWestVertex();

    public void setNorthWestVertex(int northWestVertex);

    public int getNorthEastVertex();

    public void setNorthEastVertex(int northEastVertex);

    public int getSouthEastVertex();

    public void setSouthEastVertex(int southEastVertex);

    public int getSouthWestVertex();

    public void setSouthWestVertex(int southWestVertex);

    public void setCornetVertices(ArrayList<Integer> cornetVertices);

    public ArrayList<Integer> getCornerVertices();

    public boolean isIsRightChild();

    public void setIsRightChild(boolean isRightChild);

    public void setParent(SlicingTreeNode parent);

    public SlicingTreeNode getParent();

    public ArrayList<Integer> findPath(int startVertex, int endVertex);

    public ArrayList<ArrayList<Integer>> getAllPaths();

    public void printPaths();
}
