package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;

/**
 * Einzelner Schritt eines Beweises bestehend aus der Taktik und den verwendeten
 * Parametern.
 */
public class BeweisZeileOther extends Beweiszeile{
	private String _coqString;
	
	public BeweisZeileOther(String s)
	{
		super();
		_coqString = s;
	}
	
	public String toString()
	{
		return _coqString;
	}
	
	public String toTableString()
	{
		return "<html><i><font color=\"red\">"+_coqString+"</font>";
	}
	
	public String toCoqString()
	{
		return _coqString+".";
	}
	

}
