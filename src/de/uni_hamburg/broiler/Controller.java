package de.uni_hamburg.broiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import de.uni_hamburg.broiler.gui.Notification;
import de.uni_hamburg.broiler.gui.View;
import de.uni_hamburg.broiler.logik.Beweis;
import de.uni_hamburg.broiler.logik.BeweisZeileKommentar;
import de.uni_hamburg.broiler.logik.BeweisZeileBefehl;
import de.uni_hamburg.broiler.logik.BeweisZeileTaktik;
import de.uni_hamburg.broiler.logik.Beweiszeile;
import de.uni_hamburg.broiler.logik.Taktik;
import de.uni_hamburg.broiler.schnittstelle.CoqInterface;
import de.uni_hamburg.broiler.schnittstelle.xml.XMLInterface;

public class Controller implements Observer {

	final private static String XML_PATH = "C:\\Users\\Britta\\workspace\\Broiler\\xml\\taktiken.xml";
	final private static String XSD_PATH = "C:\\Users\\Britta\\workspace\\Broiler\\xml\\taktiken.xsd";
	final private static String COQ_PATH = "C:\\Coq\\bin\\coqtop.exe";
	// final private static String FILE_PATH =
	// "C:\\Users\\Britta\\Desktop\\Hähnchen\\Coqkurs\\equality.v";
	final private static String FILE_PATH = "C:\\Users\\Britta\\Desktop\\Hähnchen\\Testprogramm.v";

	private ArrayList<Taktik> _taktiken;
	private Beweis _beweis;

	/**
	 * Kontruktor.
	 */
	public Controller() {
		try {
			_taktiken = XMLInterface.getTaktiken(XML_PATH, XSD_PATH);
		} catch (JAXBException | SAXException e) {
			System.err.println("ERROR READING XML: " + e);
			_taktiken = null;
		}

		try {
			_beweis = new Beweis(FILE_PATH, _taktiken);
		} catch (IOException e) {
			System.err.println("ERROR READING COQ FILE: " + e);
			_beweis = new Beweis();
		}

		execute(_beweis);

	}

	private void execute(Beweis b) {
		CoqInterface.executeBeweis(b, COQ_PATH);
	}

	/**
	 * Returned eine Liste mit Taktiken.
	 */
	public ArrayList<Taktik> getTaktiken() {
		// TODO Auto-generated method stub
		return _taktiken;
	}

	public Beweis getBeweis() {
		return _beweis;
	}

	@Override
	public void update(Observable o, Object arg) {
		Notification n = (Notification) arg;

		if (n.getEvent().equals("OPEN")) {

			try {
				_beweis = new Beweis((String) n.getPayload(), _taktiken);
				((View) o).changeBeweis(_beweis);
			} catch (FileNotFoundException e) {
				System.err.println("ERROR FILE NOT FOUND: " + e);
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("ERROR READING COQ FILE: " + e);
				e.printStackTrace();
			}
		} else if (n.getEvent().equals("NEW")){

			try {
				_beweis = new Beweis("", _taktiken);
				((View) o).changeBeweis(_beweis);
			} catch (FileNotFoundException e) {
				System.err.println("ERROR FILE NOT FOUND: " + e);
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("ERROR READING COQ FILE: " + e);
				e.printStackTrace();
			}
		}
	}
}
