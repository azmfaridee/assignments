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
public class SlicingTreeInternalNode implements SlicingTreeNode{
    private CutType cutType;
    private int parentId;
    private SlicingTreeNode parent;
    private ArrayList<Integer> slicingPath;
    
    public SlicingTreeInternalNode() {
        super();
    }

    public void setCutType(CutType cutType) {
        this.cutType = cutType;
    }

    public void setParent(SlicingTreeNode parent) {
        this.parent = parent;
    }

    public void setSlicingPath(ArrayList<Integer> slicingPath) {
        this.slicingPath = slicingPath;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    

}
