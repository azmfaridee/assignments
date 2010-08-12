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

    public SlicingTreeExternalNode() {
        super();
    }

    public void setFaceArea(int faceArea) {
        this.faceArea = faceArea;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    

}
