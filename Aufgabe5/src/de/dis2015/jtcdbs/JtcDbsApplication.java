package de.dis2015.jtcdbs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import  de.dis2015.jtcdbs.clients.AbstractClient;
import de.dis2015.jtcdbs.clients.Alice;
import de.dis2015.jtcdbs.clients.Bob;
import de.dis2015.jtcdbs.clients.Caesar;
import de.dis2015.jtcdbs.clients.Dimitri;
import de.dis2015.jtcdbs.clients.Eberhardt;
import de.dis2015.jtcdbs.recovery.RecoveryManager;

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

        //Recovery
        RecoveryManager recoveryManager = injector.getInstance(RecoveryManager.class);
        if (recoveryManager.isRecoveryNeeded()) {
            recoveryManager.recover();
        }

        // Create clients.
        AbstractClient a = injector.getInstance(Alice.class);
        AbstractClient b = injector.getInstance(Bob.class);
        AbstractClient c = injector.getInstance(Caesar.class);
        AbstractClient d = injector.getInstance(Dimitri.class);
        AbstractClient e = injector.getInstance(Eberhardt.class);

        a.run();
        b.run();
        c.run();
        d.run();
        e.run();
    }
}
