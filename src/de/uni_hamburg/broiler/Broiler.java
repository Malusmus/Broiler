package de.uni_hamburg.broiler;

import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import de.uni_hamburg.broiler.gui.ButtonView;
import de.uni_hamburg.broiler.gui.View;


/**
 * Mainklasse, die auch die Kommunikation zwischen Coq und der Gui regelt.
 * @author Britta
 *
 */
public class Broiler{
	
	//	private String _tacticAuslesePfad = "C:\\Users\\Britta\\workspace\\HaenchenGUI\\src\\xmlDateien\\Tactics.xml";


	//die View; wir nutzen dynamic Binding
	private static View _view;
	private static Controller _controller;
	
	/** 
	 * Mainmethode, startet das Programm.
	 * @param args
	 */
	public static void main(String[] args){
		//TODO fertig machen
		_controller = new Controller();	
		//als Standardview kommt die ButtonView zum Einsatz
		_view = new ButtonView(_controller.getTaktiken(), _controller.getBeweis());
		_view.addObserver(_controller);
		
	    SwingUtilities.invokeLater(new Runnable() {

	        @Override
	        public void run() {
	            _view.run();	        }
	    });
	}


	
}
