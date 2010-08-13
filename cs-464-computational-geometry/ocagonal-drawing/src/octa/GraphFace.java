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
public class GraphFace {
    int id;
    ArrayList<Integer> clockwiseMemberVertices;

    public GraphFace(int id) {
        this.id = id;
    }

    public void setClockwiseMemberVertices(ArrayList<Integer> clockwiseMemberVertices) {
        this.clockwiseMemberVertices = clockwiseMemberVertices;
    }

    

}
