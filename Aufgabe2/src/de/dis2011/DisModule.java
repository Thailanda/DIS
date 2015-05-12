package de.dis2011;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import de.dis2011.model.EstateAgentSecurityContext;
import de.dis2011.services.GuiService;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-04
 */
public class DisModule extends AbstractModule {

    @Override
    protected void configure() {

        // Bind GUi handling
        bind(GuiService.class).toInstance(new GuiService());

        bind(EstateAgentSecurityContext.class).toInstance(new EstateAgentSecurityContext());

    }

    /**
     * Provides the Hibernate session factory.
     */
    @Provides
    SessionFactory provideSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            Properties properties = configuration.getProperties();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
