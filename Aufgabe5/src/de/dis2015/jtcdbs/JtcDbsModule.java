package de.dis2015.jtcdbs;

import com.google.inject.AbstractModule;

import de.dis2015.jtcdbs.managers.LSNManagerImpl;
import de.dis2015.jtcdbs.managers.LogManagerImpl;
import de.dis2015.jtcdbs.managers.PersistenceManagerImpl;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-12
 */
public class JtcDbsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PersistenceManager.class).to(PersistenceManagerImpl.class);
        bind(LSNManager.class).to(LSNManagerImpl.class);
        bind(LogManager.class).to(LogManagerImpl.class);
    }
}
