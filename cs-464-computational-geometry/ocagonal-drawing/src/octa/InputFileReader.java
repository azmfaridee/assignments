/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abuzaher
 */
public class InputFileReader {

    ArrayList<String> lines;

    public InputFileReader(String fileName) {
        try {
            lines = new ArrayList<String>();
            File file = new File(fileName);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            br.close();
        } catch (IOException ex) {
            Logger.getLogger(InputFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getInputLines(){
        return this.lines;
    }
}
