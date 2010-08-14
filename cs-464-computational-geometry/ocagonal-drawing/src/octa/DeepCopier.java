/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package octa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abuzaher
 */
public class DeepCopier {

    public static Object deepCopy(Object oldObject) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            // write the object into ObjectOutputStream
            oos.writeObject(oldObject);
            // flushig is necessary
            oos.flush();

            // now create the input stream
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeepCopier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeepCopier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(DeepCopier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
