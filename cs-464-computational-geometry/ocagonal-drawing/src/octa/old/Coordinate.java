/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa.old;

/**
 *
 * @author abuzaher
 */
public class Coordinate {

    double x;
    double y;
    boolean belongsToOriginal;

    public Coordinate(double x, double y, boolean belongsToOriginal) {
        this.x = x;
        this.y = y;
        this.belongsToOriginal = belongsToOriginal;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isBelongsToOriginal() {
        return belongsToOriginal;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setBelongsToOriginal(boolean belongsToOriginal) {
        this.belongsToOriginal = belongsToOriginal;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
