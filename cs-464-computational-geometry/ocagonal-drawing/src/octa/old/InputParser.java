/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa.old;

import java.util.ArrayList;

/**
 *
 * @author abuzaher
 */
public class InputParser {

    SlicingGraph graph;
    SlicingTree tree;
    String fileName;
    ArrayList<String> inputStrings;
    int i;

    public InputParser(String fileName, SlicingGraph graph, SlicingTree tree) {
        this.fileName = fileName;
        this.tree = tree;
        this.graph = graph;
        this.inputStrings = new InputFileReader(fileName).getInputLines();
    }

    public void parseInput() {
        parseGraph();
        parseTree();
    }

    private void parseGraph() {
        // update number of vertices
        int numVertices = new Integer(inputStrings.get(i++)).intValue();
        graph.setNumVertices(numVertices);

        for (int j = 0; j < numVertices; j++) {
            String elems[] = inputStrings.get(i++).split(" ");

            ArrayList<Integer> adjList = new ArrayList<Integer>();
            for (String str : elems) {
                adjList.add(new Integer(str));
            }

            // all data for vertex j has been created
            GraphVertex gv = new GraphVertex(j);
            gv.setClockwiseAdjList(adjList);

            // vertex cration done, add the vertex to the graph
            graph.addVertex(gv);

        }


        int numFaces = new Integer(inputStrings.get(i++)).intValue();
        graph.setNumFaces(numFaces);

        for (int j = 0; j < numFaces; j++) {
            String elems[] = inputStrings.get(i++).split(" ");

            ArrayList<Integer> memberVertices = new ArrayList<Integer>();
            for (String str : elems) {
                memberVertices.add(new Integer(str));
            }

            // all data for the face j+1 has been created
            GraphFace gf = new GraphFace(j + 1);
            gf.setClockwiseMemberVertices(memberVertices);

            // face creation done, add this face to the graph
            graph.addFace(gf);

        }

//        this.printInfo();
    }

    private void parseTree() {
        // calcuation for internal nodes
        int numInternalNodes = new Integer(inputStrings.get(i++)).intValue();
        tree.setNumInternalNodes(numInternalNodes);
//        System.out.println("Number of internal nodes (cuts): " + numInternalNodes);

        for (int j = 0; j < numInternalNodes; j++) {
            SlicingTreeInternalNode internalNode = new SlicingTreeInternalNode(j + 1);

            String elems[] = inputStrings.get(i++).split(" ");

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
            tree.appendNode(internalNode);
        }


        int numLeafNodes = new Integer(inputStrings.get(i++)).intValue();
        tree.setNumExternalNodes(numLeafNodes);
//        System.out.println("Number of leaf nodes (faces): " + numLeafNodes);

        for (int j = 0; j < numLeafNodes; j++) {
            SlicingTreeExternalNode externalNode = new SlicingTreeExternalNode(j + 1);

            String elems[] = inputStrings.get(i++).split(" ");

            int parentId = new Integer(elems[0]).intValue();
            externalNode.setParentId(parentId);
//            System.out.println("Parent of leaf node (face) " + (j + 1) + ": " + parentId);

            Integer area = new Integer(elems[1]);
            externalNode.setFaceArea(area);
//            System.out.println("Area under face: " + (j + 1) + ": " + area);

            // all data for this external node has been created, add this node to slicing tree
            tree.appendNode(externalNode);

        }

//         calling the printinfo before and after updating should result in different outcome
//        slicingTree.printInfo();
        tree.updateTree();
//        slicingTree.printInfo();

        // tesing preorder traversal
//        ArrayList<SlicingTreeNode> traverserdList = tree.reversePreorderTraverse(0);
//        tree.printInfo(traverserdList);
    }
}
