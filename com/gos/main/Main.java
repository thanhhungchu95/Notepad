package com.gos.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.gos.gui.GUI;

public class Main {
	public static void main (String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.out.println(e.getMessage());
		}
		
		GUI gui = new GUI();
		gui.showWindow();
	}
}
