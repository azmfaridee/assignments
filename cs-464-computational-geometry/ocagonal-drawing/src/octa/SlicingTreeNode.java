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

    public ArrayList<Integer> getCornetVertices();
}
