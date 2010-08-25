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
public class Drawing {

    SlicingTree tree;
    SlicingGraph graph;

    public Drawing(SlicingTree tree, SlicingGraph graph) {
        this.tree = tree;
        this.graph = graph;
    }

    public void runDrawringAlgo() {
        SlicingTreeInternalNode root = tree.getRoot();
        int area = root.getFaceArea();
        double faceHeight, faceWidth;

        // we want the resulting face to be as much close to square as possible
        faceHeight = Math.floor(Math.sqrt(area));
        faceWidth = area / faceHeight;
        System.out.println("Heiht and width for root face is: " + faceHeight + " and " + faceWidth);

    }

    private ArrayList<Coordinate> getCartesianCoordinate(Coordinate leftBottom, double faceWidth, double faceHeight) {
        ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
        coordinateList.add(leftBottom);

        Coordinate rightBottom = new Coordinate(leftBottom.getX() + faceWidth, faceWidth);
        coordinateList.add(leftBottom);

        Coordinate rightTop = new Coordinate(leftBottom.getX() + faceWidth, leftBottom.getY() + faceWidth);
        coordinateList.add(rightTop);

        Coordinate leftTop = new Coordinate(leftBottom.getX(), leftBottom.getY() + faceHeight);
        coordinateList.add(leftTop);

        return coordinateList;
    }
}
