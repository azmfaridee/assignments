package aco;

import java.util.Vector;
import java.util.Scanner;
import java.io.File;

public class Input {

    public static Scanner input;
    public static String fileName;

    public static void openFile(String fileName) {
        Input.fileName = fileName;

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
    public static Vector readFile() {
        Vector<Double> vc = new Vector<Double>();
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
