package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;

/**
 * Einzelner Schritt eines Beweises bestehend aus der Taktik und den verwendeten
 * Parametern.
 */
public class BeweisZeileBefehl extends Beweiszeile {
	
	public enum Typ
	{
	QED("Qed"),
	PROOF("Proof"),
	REQUIRE_IMPORT("Require Import"),
	ADD("Add"),
	THEOREM("Theorem"),
	SECTION("Section"),
	VARIABLES("Variables"),
	PLUS("+"),
	MINUS("-"),
	STERN("*");
	
	private String _code;
	
	Typ(String s)
	{
		_code = s;
	}
	}
	
	private Logikterm _code;
	private Typ _typ;
	
	public BeweisZeileBefehl(Typ t, String s)
	{
		super();
		_code = Logikterm.fromCoqFile(s);
		_typ = t;
	}
	
	public String toString()
	{
		return _typ._code+" "+_code;
	}
	
	public String toTableString()
	{
		return "<html><b>"+_typ._code+"</b> "+_code;
	}
	
	public String toCoqString()
	{
		if (_code.toString().trim().equals(""))
			return _typ._code+".";
		else
			return _typ._code+" "+_code+".";
	}
	
	public static BeweisZeileBefehl readFromString(String s)
	{
		for (Typ t: Typ.values())
		{
			if (s.startsWith(t._code))
				return new BeweisZeileBefehl(t, s.substring(t._code.length()).trim());
		}
		return null;
	}
}
