/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

/**
 *
 * @author abuzaher
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String argv[]) {
        String fileName = "src/octa/input.txt";

        SlicingGraph graph = new SlicingGraph();
        SlicingTree tree = new SlicingTree(graph);
        InputParser parser = new InputParser(fileName, graph, tree);

        parser.parseInput();

        System.out.println("PRINTING GRAPH INFO");
        graph.printInfo();
        System.out.println("PRINTING TREE INFO");
        tree.printInfo();

        GraphDrawer drawing = new GraphDrawer(tree, graph);
        drawing.runDrawringAlgo();
//        System.out.println("REVERSE PREORDER TRAVERSAL OF TREE");
//        tree.printInfo(tree.reversePreorderTraverse(0));
    }
}
