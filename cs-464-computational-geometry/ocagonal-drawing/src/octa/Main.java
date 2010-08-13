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
        String fileName = "/Users/abuzaher/NetBeansProjects/OctagonalDraw/src/octa/input.txt";

        SlicingGraph graph = new SlicingGraph();
        graph.parseInput(fileName);
    }
}
