package de.uni_hamburg.broiler.logik;

/**
 * Klasse für den Umgang mit Zielen.
 * 
 * @author Britta
 *
 */
public class Ziel {

	String _ziel;

	public Ziel(String ziel) {
		_ziel = ziel;
	}

	public String gibZiel() {
		return _ziel;
	}

	public void append(String text) {
		_ziel = _ziel + "\n" + text;
	}

	public String toString() {
		return _ziel;
	}
}
