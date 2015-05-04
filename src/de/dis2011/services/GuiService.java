package de.dis2011.services;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-04
 */
public class GuiService {

    public GuiService() {
        initializeLAF();
    }

    private boolean initializeLAF() {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            String lafClassName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lafClassName);

            if (lafClassName.equals("com.apple.laf.AquaLookAndFeel")) {
                setUIFont(new FontUIResource("Helvetica Neue", 0, 13));
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                IllegalAccessException e) {
            // handle exception
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Sets the UI font.
     */
    private void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
