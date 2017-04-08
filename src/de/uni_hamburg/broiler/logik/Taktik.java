package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;
import java.util.List;

import de.uni_hamburg.broiler.schnittstelle.xml.StoredTaktik;

/**
 * Eine Taktik, die auf der atuellen Kombination aus Zielen und Hypothesen
 * angewandt werden soll. Benötigt eventuell weitere Parameter.
 * 
 * @author Britta
 *
 */
public class Taktik {
	// Der Typ der Taktik
	private TaktikTyp _typ;
	// der Name der Taktik
	private String _name;
	// Die Beschreibung der Taktik.
	private String _beschreibung;
	// Die Anzahl der notwendigen Parameter
	private ArrayList<Parameter> _parameter;
	// Der Code der an Coq geschickt werden soll
	private String _code;
	
	private String _label;
	
	public Taktik(StoredTaktik vorlage)
	{
		_code = vorlage.getCoqCode();
		_name = vorlage.getName();
		_beschreibung = vorlage.getBeschreibung();
		_parameter = new ArrayList<Parameter>();
		if (vorlage.getParameter() != null)
		{
			for (String s: vorlage.getParameter())
			{
				_parameter.add(new Parameter(s,""));
			}
		}
		switch(vorlage.getTyp())
		{
		  case "H": _typ = TaktikTyp.Hypothesis; break;
		  case "V": _typ = TaktikTyp.Variable; break;
		  default : _typ = TaktikTyp.None;
		}
		_label = vorlage.getButtonLabel();
		
	}

	/**
	 * Getter für den Typ.
	 * 
	 * @return
	 */
	public TaktikTyp gibTyp() {
		return _typ;
	}
	

	/**
	 * Getter für den Namen
	 */
	public String gibName() {
		return _name;
	}
	
	public String gibLabel() {
		return _label;
	}

	public String gibBeschreibung() {
		return _beschreibung;
	}

	public int gibAnzahlParameter() {
		return _parameter.size();
	}

	public String gibCode() {
		return _code;
	}

	public ArrayList<Parameter> gibParameterInfo() {
		return _parameter;
	}
}
