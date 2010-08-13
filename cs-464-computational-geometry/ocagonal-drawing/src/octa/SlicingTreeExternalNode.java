/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package octa;

/**
 *
 * @author abuzaher
 */
public class SlicingTreeExternalNode implements SlicingTreeNode{
    private int faceArea;
    private int parentId;
    private int id;

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

}
