package de.dis2011;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.dis2011.gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-21
 */
public class Main {

    private final Injector injector;

    public static void main(final String[] args) throws Exception {
        Main application = new Main();
        application.run();
    }

    public Main() {
        injector = Guice.createInjector(new DisModule());
    }

    /**
     * Runs the application.
     */
    private void run() {
        SwingUtilities.invokeLater(() -> {
            final MainFrame GUI = injector.getInstance(MainFrame.class);
            GUI.showGui();
        });
    }
}
