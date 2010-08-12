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
//    ArrayList<GraphVertex> clockwiseAdjList;
    ArrayList<Integer> clockwiseAdjList;
    int id;

    public GraphVertex(int id) {
        this.id = id;
    }

    public void setClockwiseAdjList(ArrayList<Integer> clockwiseAdjList) {
        this.clockwiseAdjList = clockwiseAdjList;
    }    

}
