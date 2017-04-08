package de.uni_hamburg.broiler.logik;

/**
 * Klasse, die für mögliche Parameter steht. Dies können Hypothesen und Logikterme sein.
 * @author Britta
 *
 */
public class Parameter {
	
	private String _name;
	protected String _code;
	
	public Parameter(String name, String code){
		_name = name;
		_code = code;
	}
	
	public  String gibName(){
		return _name;
	}
	
	public void setCode(String code)
	{
		_code = code;
	}

	public  String gibCode(){
		return _code;
	}
	
}
