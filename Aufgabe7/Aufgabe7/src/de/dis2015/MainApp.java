package de.dis2015;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.hibernate.Session;

/**
 * Created by Joanna on 25.06.2015.
 */
public class MainApp {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DataWarehouseModule());

        Session session = injector.getInstance(Session.class);
    }
}
