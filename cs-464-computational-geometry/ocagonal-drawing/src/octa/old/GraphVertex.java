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
public class GraphVertex {

    private ArrayList<Integer> clockwiseAdjList;
    private int id;
    private int numAdjVertex;
    private boolean threeDegVertex;
    private Coordinate coordinate;

    public GraphVertex(int id) {
        this.id = id;
        this.threeDegVertex = false;
    }

    public boolean isThreeDegVertex() {
        return threeDegVertex;
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
        if (numAdjVertex > 2) {
            threeDegVertex = true;
        } else {
            threeDegVertex = false;
        }
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
