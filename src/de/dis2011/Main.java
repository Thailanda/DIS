package de.dis2011;

import de.dis2011.gui.MainFrame;
import java.util.Map;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-21
 */
public class Main {


    public static void main(final String[] args) throws Exception {
        Main application = new Main();

        if (!application.initSwing()) {
            return;
        }

        final SessionFactory factory = application.initHibernate();
        final Session session = factory.openSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }

        application.run(factory);
    }

    /**
     * Initializes swing.
     */
    private boolean initSwing() {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            String lafClassName = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lafClassName);

            if (lafClassName.equals("com.apple.laf.AquaLookAndFeel")) {
                Main.setUIFont(new FontUIResource("Helvetica Neue", 0, 13));
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
     * Initializes hibernate and returns a session factory.
     */
    private SessionFactory initHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties
                    ()).buildServiceRegistry();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Runs the application.
     */
    private void run(final SessionFactory factory) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final MainFrame GUI = new MainFrame(factory);
                GUI.showGui();
            }
        });
    }

    /**
     * Sets the UI font.
     */
    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
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
