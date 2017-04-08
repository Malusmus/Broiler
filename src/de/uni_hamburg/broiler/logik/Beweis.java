package de.uni_hamburg.broiler.logik;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.ErrorManager;

/**
 * Container für einen zusammenhängenden Satz an Beweisbestandteilen.
 * 
 * @author Britta
 *
 */
public class Beweis {
	private ArrayList<Beweiszeile> _beweis;
	private String _errorMessage;

	public Beweis() {
		_beweis = new ArrayList<Beweiszeile>();
		_errorMessage = "";
	}

	public ArrayList<Beweiszeile> gibZeilen() {
		return _beweis;
	}

	public String gibCode() {
		String code = "";
		if (_beweis != null) {
			for (Beweiszeile b : _beweis) {
				code = code + b.toCoqString() + "\n";
			}
		}
		return code;
	}

	public void setError(String s) {
		_errorMessage = s;
	}

	public String getError() {
		return _errorMessage;
	}

	public void insertZeile(int stelle, Beweiszeile zeile) {
		if (stelle == -1) // ganz hinten
			_beweis.add(zeile);
		else
			_beweis.add(stelle, zeile);
	}

	public void parseAndAddZeile(String zeile, ArrayList<Taktik> taktiken) {
		zeile = zeile.trim();
		if (zeile.equals(""))
			return;

		BeweisZeileBefehl istBefehl = BeweisZeileBefehl.readFromString(zeile);
		if (istBefehl != null) {
			insertZeile(-1, istBefehl);
			return;
		}
		BeweisZeileTaktik istTaktik = BeweisZeileTaktik.readFromString(zeile, taktiken);
		if (istTaktik != null) {
			insertZeile(-1, istTaktik);
			return;
		}

		insertZeile(-1, new BeweisZeileOther(zeile));

		// Wenn wir hier sind, wurde die Zeile nicht erfolgreich interpretiert

		System.err.println("Unidentifizierte Zeile " + zeile);

	}

	public Beweis(String file, ArrayList<Taktik> taktiken) throws FileNotFoundException, IOException {
		this();
		if (!file.equals("")) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				String everything = sb.toString();
				while (!everything.trim().equals("")) {
					// solange noch Text übrig ist:
					int punkt = everything.indexOf(".");
					int auf = everything.indexOf("(*");
					if (punkt < auf || (punkt > -1 && auf == -1)) {
						// Befehl abarbeiten
						String zeile = everything.substring(0, punkt).trim();
						parseAndAddZeile(zeile, taktiken);
						everything = everything.substring(punkt + 1);
					} else if (auf > -1) {
						// Kommentar abarbeiten
						int kommentarAnfang = everything.indexOf("(*");
						int tiefe = 0;
						String bisKommentar = everything.substring(0, kommentarAnfang);
						String kommentar = "";
						String rest = everything.substring(kommentarAnfang);
						do {
							auf = rest.indexOf("(*");
							int zu = rest.indexOf("*)");
							if (auf < zu && auf > -1) {
								// als nächstes öffnende
								kommentar = kommentar + rest.substring(0, auf + 2);
								rest = rest.substring(auf + 2);
								tiefe++;
							} else if (zu > -1) {
								// als nächstes schließende
								kommentar = kommentar + rest.substring(0, zu + 2);
								rest = rest.substring(zu + 2);
								tiefe--;
							} else
								tiefe = -1;
						} while (tiefe > 0);

						if (tiefe == -1)
							System.err.println("Ungültige Kommentarschachtelung");
						insertZeile(-1, new BeweisZeileKommentar(kommentar));
						everything = bisKommentar + rest;
					}
				}
			}
		}
	}

}
