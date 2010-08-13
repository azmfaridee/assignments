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
}
