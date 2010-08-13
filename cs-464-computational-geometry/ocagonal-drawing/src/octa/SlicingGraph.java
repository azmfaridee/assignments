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
public class SlicingGraph {

    private int numVertices;
    private ArrayList<GraphVertex> vertices;
    private int numFaces;
    private ArrayList<GraphFace> faces;

    public SlicingGraph() {
        this.vertices = new ArrayList<GraphVertex>();
        this.faces = new ArrayList<GraphFace>();
    }

    public void addVertex(GraphVertex gv) {
        this.vertices.add(gv);
    }

    public void addFace(GraphFace gf) {
        this.faces.add(gf);
    }

    public int getNumVertex() {
        return this.vertices.size();
    }

    public int getNumFaces() {
        return this.faces.size();
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public void setNumFaces(int numFaces) {
        this.numFaces = numFaces;
    }

    public void printInfo() {
        System.out.println("Number of vertices in the graph: " + this.numVertices);
        int i = 0;
        for (GraphVertex graphVertex : vertices) {
            System.out.println("Adjacency List for vertex " + (i++) + ": " + graphVertex.clockwiseAdjList);
        }
        System.out.println("Number of faces in the graph: " + this.numFaces);
        i = 1;
        for (GraphFace graphFace : faces) {
            System.out.println("List of nodes in face: " + (i++) + ": " + graphFace.clockwiseMemberVertices);
        }

    }

    public void parseInput(String fileName) {
        ArrayList<String> inputStings = new InputFileReader(fileName).getInputLines();

        int i = 0;
        // update number of vertices
        @SuppressWarnings("LocalVariableHidesMemberVariable")
        int numVertices = new Integer(inputStings.get(i++)).intValue();
        this.setNumVertices(numVertices);

        for (int j = 0; j < numVertices; j++) {
            String elems[] = inputStings.get(i++).split(" ");

            ArrayList<Integer> adjList = new ArrayList<Integer>();
            for (String str : elems) {
                adjList.add(new Integer(str));
            }

            // all data for vertex j has been created
            GraphVertex gv = new GraphVertex(j);
            gv.setClockwiseAdjList(adjList);

            // vertex cration done, add the vertex to the graph
            this.addVertex(gv);

        }


        int numFaces = new Integer(inputStings.get(i++)).intValue();
        this.setNumFaces(numFaces);

        for (int j = 0; j < numFaces; j++) {
            String elems[] = inputStings.get(i++).split(" ");

            ArrayList<Integer> memberVertices = new ArrayList<Integer>();
            for (String str : elems) {
                memberVertices.add(new Integer(str));
            }

            // all data for the face j+1 has been created
            GraphFace gf = new GraphFace(j + 1);
            gf.setClockwiseMemberVertices(memberVertices);

            // face creation done, add this face to the graph
            this.addFace(gf);

        }

//        this.printInfo();




        // input processing for the slicing tree
        // create a slicing tree
        SlicingTree slicingTree = new SlicingTree();

        // calcuation for internal nodes
        int numInternalNodes = new Integer(inputStings.get(i++)).intValue();
        slicingTree.setNumInternalNodes(numInternalNodes);
//        System.out.println("Number of internal nodes (cuts): " + numInternalNodes);

        for (int j = 0; j < numInternalNodes; j++) {
            SlicingTreeInternalNode internalNode = new SlicingTreeInternalNode(j + 1);

            String elems[] = inputStings.get(i++).split(" ");

            // right now just set the parentId, we shall update the references with actual parents later
            int parent = new Integer(elems[0]).intValue();
            internalNode.setParentId(parent);
//            System.out.println("Parent of inernal node (cut)" + (j + 1) + ": " + parent);


            // update cutType
            CutType cutType = null;
            int cutTypeIntValue = new Integer(elems[1]).intValue();
            if (cutTypeIntValue == 0) {
                cutType = CutType.VERTICAL;
            } else {
                if (cutTypeIntValue == 1) {
                    cutType = CutType.HORIZONTAL;
                }
            }
            internalNode.setCutType(cutType);
//            System.out.println("Node Type: " + cutType);


            // update slicing path
            ArrayList<Integer> slicingPath = new ArrayList<Integer>();
            for (int k = 2; k < elems.length; k++) {
                slicingPath.add(new Integer(elems[k]));
            }
            internalNode.setSlicingPath(slicingPath);
//            System.out.println("Slicing path for this node: " + slicingPath);

            // all data for the intrenal nodes has been created, add this node to slicing tree
            slicingTree.appendNode(internalNode);
        }


        int numLeafNodes = new Integer(inputStings.get(i++)).intValue();
        slicingTree.setNumExternalNodes(numLeafNodes);
//        System.out.println("Number of leaf nodes (faces): " + numLeafNodes);

        for (int j = 0; j < numLeafNodes; j++) {
            SlicingTreeExternalNode externalNode = new SlicingTreeExternalNode(j + 1);

            String elems[] = inputStings.get(i++).split(" ");

            int parentId = new Integer(elems[0]).intValue();
            externalNode.setParentId(parentId);
//            System.out.println("Parent of leaf node (face) " + (j + 1) + ": " + parentId);

            Integer area = new Integer(elems[1]);
            externalNode.setFaceArea(area);
//            System.out.println("Area under face: " + (j + 1) + ": " + area);

            // all data for this external node has been created, add this node to slicing tree
            slicingTree.appendNode(externalNode);

        }

//         calling the printinfo before and after updating should result in different outcome
//        slicingTree.printInfo();
        slicingTree.updateTree();
//        slicingTree.printInfo();

        // tesing preorder traversal
        ArrayList<SlicingTreeNode> traverserdList = slicingTree.reversePreorderTraverse(0);
        slicingTree.printInfo(traverserdList);
    }
}
