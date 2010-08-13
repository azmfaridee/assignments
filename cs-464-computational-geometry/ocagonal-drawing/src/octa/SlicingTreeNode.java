/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package octa;

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
}
