

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author abuzaher
 */
public class Reader {

    private static Scanner input;

    public static void openFile(String fileName) {

        try {
            input = new Scanner(new File(fileName));
        } catch (Exception ex) {
            System.out.println("error  opening File ");
            System.exit(1);
        }
    }

    /*
     * Reads all the data in the file in linear fashion
     * the 1D Vector vc will have the 2D data as well as alpha and beta
     * parametes
     */
    public static ArrayList<Double> readFile() {
        ArrayList<Double> vc = new ArrayList<Double>();
        while (input.hasNext()) {
            vc.add(input.nextDouble());
        }
        return vc;
    }

    public static void closeFile() {
        if (input != null) {
            input.close();
        }
    }
}
