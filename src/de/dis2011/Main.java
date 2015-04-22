package de.dis2011;

import de.dis2011.gui.MainFrame;
import java.util.Map;
import javax.swing.SwingUtilities;
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
        Main main = new Main();
        final SessionFactory factory = main.initHibernate();
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

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    final MainFrame GUI = new MainFrame(factory);
                    GUI.showGui();
                }
            });
        } finally {
            session.close();
        }
    }

    /**
     * Initializes hibernate and returns a session factory.
     */
    private SessionFactory initHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
