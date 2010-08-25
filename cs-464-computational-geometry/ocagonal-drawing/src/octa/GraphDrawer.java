/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author abuzaher
 */
public class GraphDrawer {

    SlicingTree tree;
    SlicingGraph graph;
    ArrayList<DrawnFace> drawnFaces;

    public GraphDrawer(SlicingTree tree, SlicingGraph graph) {
        this.tree = tree;
        this.graph = graph;
        drawnFaces = new ArrayList<DrawnFace>();
    }

    public void runDrawringAlgo() {
//        int area = root.getFaceArea();
//        double faceHeight, faceWidth;
//
//        // we want the resulting face to be as much close to square as possible
//        faceHeight = Math.floor(Math.sqrt(area));
//        faceWidth = area / faceHeight;
//        System.out.println("Heiht and width for root face is: " + faceHeight + " and " + faceWidth);
//        System.out.println(getCartesianCoordinate(new Coordinate(0, 0), faceWidth, faceHeight));

//        System.out.println(tree.reversePreorderTraverse(0));
        ArrayList<SlicingTreeNode> traversedList = tree.reversePreorderTraverse(0);
        for (Iterator<SlicingTreeNode> it = traversedList.iterator(); it.hasNext();) {
            SlicingTreeNode slicingTreeNode = it.next();
            if (slicingTreeNode instanceof SlicingTreeInternalNode) {
                if (slicingTreeNode.getId() == 1) {
                    // we have the root node
                    DrawnFace drawnRootFace = new DrawnFace();
//                    drawnRootFace.
                } else {
                    // we have other Internal nodes that are not root
                }

            } else {
                // we have leaf node
            }

        }

    }

    private ArrayList<Coordinate> getCartesianCoordinate(Coordinate leftBottom, double faceWidth, double faceHeight) {
        ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
        coordinateList.add(leftBottom);

        Coordinate rightBottom = new Coordinate(leftBottom.getX() + faceWidth, leftBottom.getY(), true);
        coordinateList.add(rightBottom);

        Coordinate rightTop = new Coordinate(leftBottom.getX() + faceWidth, leftBottom.getY() + faceWidth, true);
        coordinateList.add(rightTop);

        Coordinate leftTop = new Coordinate(leftBottom.getX(), leftBottom.getY() + faceHeight, true);
        coordinateList.add(leftTop);

        return coordinateList;
    }

    private boolean isHorizontalCrossInside(Coordinate topLeft, Coordinate bottomLeft, Coordinate right) {
        if (right.getY() < topLeft.getY() && right.getY() > bottomLeft.getY()) {
            return true;
        }
        return false;
    }
}
