/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.util.ArrayList;

/**
 *
 * @author abuzaher
 * We use this list to make some algorithms simpler
 */
public class CircularList<E> {

    ArrayList<E> list;
    E cursor;

    public CircularList(ArrayList<E> list, E cursor) throws Exception {
        this.list = list;
        if (this.list.indexOf(cursor) == -1) {
            throw new Exception("Invalid cursor");
        } else {
            this.cursor = cursor;
        }

    }

    public E getNext() {
        int pos = this.list.indexOf(this.cursor);
        if (pos == this.list.size() - 1) {
            cursor = this.list.get(0);
        } else {
            cursor = this.list.get(pos + 1);
        }
        return cursor;
    }

    public E getPrev() {
        int pos = this.list.indexOf(this.cursor);
        if (pos == 0) {
            cursor = this.list.get(this.list.size() - 1);
        } else {
            cursor = this.list.get(pos - 1);
        }
        return cursor;
    }
}
