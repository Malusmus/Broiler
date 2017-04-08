package de.uni_hamburg.broiler.logik;

import java.util.ArrayList;

/**
 * Interface für die Gesamtheit aller erlaubten Komponenten in einem Beweis.
 * @author Britta
 *
 */
public abstract class Beweiszeile {
	
	private ArrayList<Hypothese> hypothesen;
	private ArrayList<Ziel> ziele;

	public abstract String toString();
	
    public abstract String toTableString();
    
    public abstract String toCoqString();
    
    public void setZiele(ArrayList<Ziel> z)
    {
    	ziele = z;
    }
    
    public void setHypothesen(ArrayList<Hypothese> h)
    {
    	hypothesen = h;
    }
    
    public ArrayList<Hypothese> getHypothesen()
    {
    	return hypothesen;
    }
    
    public ArrayList<Ziel> getZiele()
    {
    	return ziele;
    }
    
    
}
