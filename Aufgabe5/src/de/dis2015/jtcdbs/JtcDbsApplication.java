package de.dis2015.jtcdbs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.dis2015.jtcdbs.clients.Alice;
import de.dis2015.jtcdbs.clients.Bob;
import de.dis2015.jtcdbs.clients.Caesar;
import de.dis2015.jtcdbs.clients.Dimitri;
import de.dis2015.jtcdbs.clients.Eberhardt;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-12
 */
public class JtcDbsApplication {

    public static void main(final String[] args) {
        new JtcDbsApplication();
    }

    private JtcDbsApplication() {
        Injector injector = Guice.createInjector(new JtcDbsModule());

        // Create clients.
        injector.getInstance(Alice.class);
        injector.getInstance(Bob.class);
        injector.getInstance(Caesar.class);
        injector.getInstance(Dimitri.class);
        injector.getInstance(Eberhardt.class);
    }
}
