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
}
