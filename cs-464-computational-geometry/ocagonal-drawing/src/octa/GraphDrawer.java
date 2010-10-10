/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.util.ArrayList;
import java.util.List;

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

    public int getNumThreeDegreeVertex(ArrayList<Integer> path) {
        int numThreeDegVertex = 0;
        List<Integer> subpath = path.subList(1, path.size() - 1);
        for (Integer i : subpath) {
            if (graph.getVertexById(i).isThreeDegVertex()) {
                numThreeDegVertex += 1;
            }
        }
        return numThreeDegVertex;
    }

    private void reversePreorderDraw(SlicingTreeNode node) {
        System.out.println("VISITING" + node.getId());
        ArrayList<Integer> cornerVertices = node.getCornerVertices();
        // if you are in the leaf node,
        if (node instanceof SlicingTreeExternalNode) {
            SlicingTreeExternalNode eNode = (SlicingTreeExternalNode) node;
            // base case
            // draw the west path
            ArrayList<Integer> westPath = eNode.getAllPaths().get(3);
            List<Integer> westPathInternalVertices = westPath.subList(1, westPath.size() - 1);
//            System.out.println(westPathInternalVertices);

            ArrayList<Coordinate> cornerCoordinates = new ArrayList<Coordinate>();
            for (int i = 0; i < 4; i++) {
//                System.out.println(cornerVertices.get(i));;
            }
//            double westPathVertexRatio = (eNode.get.get(1).getY() - cornerCoordinates.get(2).getY()) / (eastPathInternalVertices.size() + 1);
            return;
        }
        // we have an internal node
        SlicingTreeInternalNode iNode = (SlicingTreeInternalNode) node;

        if (iNode.getCutType() == CutType.VERTICAL) {
            // if vertical cut
            // need to set yn and ys
            int leftChildArea = iNode.getLeftChild().getFaceArea();
            int rightChildArea = iNode.getRightChild().getFaceArea();
            double leftRightReatio = leftChildArea / (rightChildArea + 0.0);

            Coordinate yn = getInternalPointByRatio(graph.getVertexById(iNode.getNorthWestVertex()).getCoordinate(), graph.getVertexById(iNode.getNorthEastVertex()).getCoordinate(), leftRightReatio, true);
            Coordinate ys = getInternalPointByRatio(graph.getVertexById(iNode.getSouthWestVertex()).getCoordinate(), graph.getVertexById(iNode.getSouthEastVertex()).getCoordinate(), leftRightReatio, true);
//            System.out.println(yn);
//            System.out.println(ys);

            ArrayList<Integer> slicingPath = iNode.getSlicingPath();

            // set yn and ys
            graph.getVertexById(slicingPath.get(0)).setCoordinate(yn);
            graph.getVertexById(slicingPath.get(slicingPath.size() - 1)).setCoordinate(ys);

            // set only the x value for all the vertices
        } else {
            // horizonal cut
            ArrayList<Integer> slicingPath = iNode.getSlicingPath();
            Coordinate yw, ye;
            ye = graph.getVertexById(slicingPath.get(slicingPath.size() - 1)).getCoordinate();
            double ywXCord = graph.getVertexById(slicingPath.get(0)).getCoordinate().getX();
            double ywTempYCord = ye.getY();
            Coordinate tempCord = new Coordinate(ywXCord, ywTempYCord, true);
        }

        reversePreorderDraw(iNode.getRightChild());
        reversePreorderDraw(iNode.getLeftChild());
    }

    public void runDrawringAlgo() {
        ArrayList<SlicingTreeNode> traversedList = tree.reversePreorderTraverse(0);

        SlicingTreeInternalNode rootNode = tree.getRoot();
        int minFaceArea = tree.getMinFaceArea();
        int graphArea = rootNode.getFaceArea();
        double lambda;

        double faceHeight, faceWidth;
        // we want the resulting face to be as much close to square as possible
        faceHeight = Math.floor(Math.sqrt(graphArea));
        faceWidth = graphArea / faceHeight;

        lambda = faceWidth * minFaceArea / (tree.getNumInternalNodes() * graphArea);

        ArrayList<Coordinate> cornerCoordinates = getCartesianCoordinate(new Coordinate(0, 0, true), faceWidth, faceHeight);
//        System.out.println(cornerCoordinates);

        // update the four corner vertices
        ArrayList<Integer> cornerVertices = rootNode.getCornerVertices();
        for (int i = 0; i < 4; i++) {
            graph.getVertexById(cornerVertices.get(i)).setCoordinate(cornerCoordinates.get((i + 1) % 4));
//            System.out.println( graph.getVertexById(cornerVertices.get(i)).getCoordinate());
        }

        ArrayList<ArrayList<Integer>> paths = rootNode.getAllPaths();
        ArrayList<Integer> eastPath = paths.get(1);
        List<Integer> eastPathInternalVertices = eastPath.subList(1, eastPath.size() - 1);

        // B - C
        double eastPathVertexRatio = (cornerCoordinates.get(1).getY() - cornerCoordinates.get(2).getY()) / (eastPathInternalVertices.size() + 1);
        for (int i = 1; i <= eastPathInternalVertices.size(); i++) {
            double x = cornerCoordinates.get(1).getX();
            double y = cornerCoordinates.get(1).getY() - eastPathVertexRatio * i;
            Coordinate c = new Coordinate(x, y, true);
//            System.out.println(c);
            int index = eastPathInternalVertices.get(i - 1);
            graph.getVertexById(index).setCoordinate(c);
        }

        reversePreorderDraw(rootNode);

//        for (Iterator<SlicingTreeNode> it = traversedList.iterator(); it.hasNext();) {
//            SlicingTreeNode slicingTreeNode = it.next();
//            if (slicingTreeNode instanceof SlicingTreeInternalNode) {
//                SlicingTreeInternalNode inode = (SlicingTreeInternalNode) slicingTreeNode;
//
//                if (inode.getId() == 1) {
//                    // we have the root node
//                    // this is the initialization part
//
//                    DrawnFace drawnRootFace = new DrawnFace();
//                    int area = inode.getFaceArea();
//
//                    double faceHeight, faceWidth;
//                    // we want the resulting face to be as much close to square as possible
//                    faceHeight = Math.floor(Math.sqrt(area));
//                    faceWidth = area / faceHeight;
//
//                    System.out.println("Main graph will have width " + faceWidth + " and Height" + faceHeight);
//
//                    // calculate lambda
//                    lambda = faceWidth * minFaceArea / (tree.getNumInternalNodes() * area);
//                    System.out.println("Lambda: " + lambda);
//
//                    // set to four corner vertices
//                    ArrayList<Coordinate> cornerCoordinates = getCartesianCoordinate(new Coordinate(0, 0, true), faceWidth, faceHeight);
//                    drawnRootFace.setCornerVertices(cornerCoordinates);
//
//                    System.out.println("Corner vertex " + drawnRootFace);
//
//                    // get the north, south, east and west paths
//                    ArrayList<ArrayList<Integer>> paths = inode.getAllPaths();
//                    ArrayList<Integer> eastPath = paths.get(1);
////                    System.out.println(eastPath);
//
////                    System.out.println(getNumThreeDegreeVertex(eastPath));
//                    List<Integer> eastPathInternalVertices = eastPath.subList(1, eastPath.size() - 1);
////                    System.out.println(eastPathInternalVertex);
//                    double eastPathVertexRatio = (drawnRootFace.getB().getY() - drawnRootFace.getC().getY()) / (eastPathInternalVertices.size() + 1);
//                    for (int i = 1; i <= eastPathInternalVertices.size(); i++) {
//                        double x = drawnRootFace.getB().getX();
//                        double y = drawnRootFace.getB().getY() - eastPathVertexRatio * i;
//                        Coordinate c = new Coordinate(x, y, true);
//                        drawnRootFace.getEastPathVertices().add(c);
//                    }
//
//                    // check for cut type
//                    if (inode.getCutType() == CutType.VERTICAL) {
//                        // if vertical cut
//                        // need to set yn and ys
//                        int leftChildArea = inode.getLeftChild().getFaceArea();
//                        int rightChildArea = inode.getRightChild().getFaceArea();
//                        double leftRightReatio = leftChildArea / rightChildArea;
//
//                        Coordinate yn = getInternalPointByRatio(drawnRootFace.getA(), drawnRootFace.getB(), leftRightReatio, true);
//                        Coordinate ys = getInternalPointByRatio(drawnRootFace.getD(), drawnRootFace.getC(), leftRightReatio, true);
//                        drawnRootFace.setYn(yn);
//                        drawnRootFace.setYs(ys);
//                    } else {
//                        // if horizontal cut
//                    }
//                } else {
//                    // we have other Internal nodes that are not root
//                }
//
//            } else {
//                // we have leaf node
//            }
//
//        }

    }

    private ArrayList<Coordinate> getCartesianCoordinate(Coordinate leftBottom, double faceWidth, double faceHeight) {
        ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
        Coordinate rightBottom = new Coordinate(leftBottom.getX() + faceWidth, leftBottom.getY(), true);
        Coordinate rightTop = new Coordinate(leftBottom.getX() + faceWidth, leftBottom.getY() + faceHeight, true);
        Coordinate leftTop = new Coordinate(leftBottom.getX(), leftBottom.getY() + faceHeight, true);

        coordinateList.add(leftTop);
        coordinateList.add(rightTop);
        coordinateList.add(rightBottom);
        coordinateList.add(leftBottom);
        return coordinateList;
    }

    private boolean isHorizontalCrossInside(Coordinate topLeft, Coordinate bottomLeft, Coordinate right) {
        if (right.getY() < topLeft.getY() && right.getY() > bottomLeft.getY()) {
            return true;
        }
        return false;
    }

    private Coordinate getInternalPointByRatio(Coordinate first, Coordinate second, double ratio, boolean belongsToOriginal) {
        double x = (ratio * second.getX() + first.getX()) / (ratio + 1);
        double y = (ratio * second.getY() + first.getY()) / (ratio + 1);
        Coordinate c = new Coordinate(x, y, belongsToOriginal);
        return c;
    }
}
