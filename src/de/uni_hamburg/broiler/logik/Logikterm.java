package de.uni_hamburg.broiler.logik;

/**
 * Klasse zum Umgang und zur Erzeugung von Logiktermen, entweder als Parameter
 * für eine Taktik oder als eigenständiges Element zum Beispiel zum Erzeugen des
 * Beweisanfangs.
 * 
 * @author Britta
 *
 */
public class Logikterm  {
	
	private static final char[] coqChar = {'∧','→','∨'};
	private static final char[] displayChar = {'∧','→','∨'};	
	
	private String _term;
	
	private Logikterm()
	{

	}
	
	public static Logikterm fromCoqFile(String s)
	{
		Logikterm t = new Logikterm();
		t._term = s;
		return t;
	}


	public String toString() {
		return _term;
	}
}
