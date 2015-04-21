package de.dis2011;

import javax.swing.SwingUtilities;

import de.dis2011.gui.MainFrame;

/**
 * Hauptklasse
 */
public class Main {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
//		TODO Implement Stuff
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 final MainFrame GUI = new MainFrame();
                 GUI.showGui();
            }

       });
	}
}
