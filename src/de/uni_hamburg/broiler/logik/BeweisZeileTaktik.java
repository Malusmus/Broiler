package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;


import de.uni_hamburg.broiler.logik.BeweisZeileBefehl.Typ;

/**
 * Einzelner Schritt eines Beweises bestehend aus der Taktik und den verwendeten
 * Parametern.
 */
public class BeweisZeileTaktik extends Beweiszeile{
	private Taktik _taktik;
	private ArrayList<Parameter> _parameter;

	public BeweisZeileTaktik(Taktik taktik)
	{
		super();
		_taktik = taktik;
		_parameter = new ArrayList<Parameter>();
		if (_taktik.gibParameterInfo() != null)
		{
			for (Parameter p:_taktik.gibParameterInfo())
				_parameter.add(new Parameter(p.gibName(),p.gibCode()));
		}
	}
	

	public ArrayList<Parameter> getParameter()
	{
		return _parameter;
	}
	
	public String toString(){
		String s = _taktik.gibBeschreibung();
		if (_parameter!=null)
		for (int i = 0; i < _parameter.size(); ++i){
			s = s + " " + _parameter.get(i).gibName();
		}
		return s + ". \n";
	}
	
	public static BeweisZeileTaktik readFromString(String s, ArrayList<Taktik> taktiken)
	{
		for (Taktik t: taktiken)
		{
			if (s.startsWith(t.gibCode()+" ") || s.equals(t.gibCode()))
			{
				BeweisZeileTaktik z = new BeweisZeileTaktik(t);
				if (t.gibParameterInfo() != null && t.gibParameterInfo().size() > 0)
				{
					s = s.substring(t.gibCode().length()).trim();
					String[] parms = s.split(" ");
					if (parms.length > 0)
					{
						for (int i=0; i<parms.length; i++)
							z._parameter.get(i).setCode(parms[i]);
					}
				}
				return z;
			}
		}
		return null;
	}
	
	public String toTableString()
	{
		String s = "<html><i><font color=\"green\">"+_taktik.gibName();
		if (_parameter != null){
			s = s+" ";
			for (int i = 0; i < _parameter.size(); ++i){
				s = s + " <b>" + _parameter.get(i).gibName()+"</b>";
		}
	}
		return s + "</html>";
	}
	
	public String toCoqString()
	{
		String s = _taktik.gibCode();
		if (_parameter != null){
			s = s+" ";
			for (int i = 0; i < _parameter.size(); ++i){
				s = s + _parameter.get(i).gibCode();
		}
	}
		return s+".";
	}
}
