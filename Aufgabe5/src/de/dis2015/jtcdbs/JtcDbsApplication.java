package de.dis2015.jtcdbs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.dis2011.DisModule;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-12
 */
public class JtcDbsApplication {

    public static void main(final String[] args) {
        new JtcDbsApplication();
    }

    private JtcDbsApplication() {
        Injector injector = Guice.createInjector(new DisModule());
    }
}
