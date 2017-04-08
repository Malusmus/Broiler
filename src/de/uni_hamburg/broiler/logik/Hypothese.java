package de.uni_hamburg.broiler.logik;

/**
 * Klasse zum Handeln von Hypothesen.
 * 
 * @author Britta
 *
 */
public class Hypothese extends Parameter {

	public Hypothese(String name, String code) {
		super(name, code);
		// TODO Auto-generated constructor stub
	}
	
	public void append(String text){
		_code = _code + "\n" + text;
	}
	
	public String toString() {
		return _code;
	}
}
