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
public class GraphVertex {
    private ArrayList<Integer> clockwiseAdjList;
    private int id;
    private int numAdjVertex;

    public GraphVertex(int id) {
        this.id = id;
    }

    /**
     * @return the clockwiseAdjList
     */
    public ArrayList<Integer> getClockwiseAdjList() {
        return clockwiseAdjList;
    }

    /**
     * @param clockwiseAdjList the clockwiseAdjList to set
     */
    public void setClockwiseAdjList(ArrayList<Integer> clockwiseAdjList) {
        this.clockwiseAdjList = clockwiseAdjList;
        this.numAdjVertex = clockwiseAdjList.size();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getNumAdjVertex() {
        return numAdjVertex;
    }


}
