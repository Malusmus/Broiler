package de.uni_hamburg.broiler.schnittstelle.xml;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import de.uni_hamburg.broiler.logik.Taktik;

public class XMLInterface
{
	public static ArrayList<Taktik> getTaktiken(String xmlFile, String schemaFile) throws JAXBException, SAXException
	{
		TaktikDef td;
		td = JaxbMarshalUnmarshalUtil.unmarshal(schemaFile, xmlFile, TaktikDef.class);
		ArrayList<Taktik> result = new ArrayList<Taktik>();
		for (StoredTaktik stored: td.getTaktik())
		{
			result.add(new Taktik(stored));
		}
		return result;
	}
}