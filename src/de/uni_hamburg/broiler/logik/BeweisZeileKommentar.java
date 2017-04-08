package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;

/**
 * Einzelner Schritt eines Beweises bestehend aus der Taktik und den verwendeten
 * Parametern.
 */
public class BeweisZeileKommentar extends Beweiszeile{
	private String _kommentar;
	
	public BeweisZeileKommentar(String s)
	{
		super();
		_kommentar = s;
	}
	
	public String toString()
	{
		return _kommentar;
	}
	
	public String toTableString()
	{
		return "<html><i><font color=\"gray\">"+_kommentar+"</font>";
	}
	
	public String toCoqString()
	{
		return _kommentar;
	}
	

}
