package de.uni_hamburg.broiler.schnittstelle;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import de.uni_hamburg.broiler.logik.Beweis;
import de.uni_hamburg.broiler.logik.BeweisZeileKommentar;
import de.uni_hamburg.broiler.logik.Beweiszeile;
import de.uni_hamburg.broiler.logik.Hypothese;
import de.uni_hamburg.broiler.logik.Ziel;
import de.uni_hamburg.broiler.schnittstelle.xml.StreamGobbler;

public class CoqInterface {

	public static Beweis executeBeweis(Beweis beweis, String executable) {
		
		ArrayList<String> out = new ArrayList<>();
		ArrayList<String> err = new ArrayList<>();

		Thread t1 = null;
		Thread t2 = null;

		try {

			// executes the command
			Process process = Runtime.getRuntime().exec(executable);

			// create the stream gobblers, one for the input stream and one for
			// the
			// error stream. these gobblers will consume these streams.
			StreamGobbler sgInput = new StreamGobbler(process.getInputStream(), out, 'O');
			StreamGobbler sgError = new StreamGobbler(process.getErrorStream(), err, 'E');

			// creates a thread for each stream gobbler and start them
			t1 = new Thread(sgInput);
			t2 = new Thread(sgError);
			t1.start();
			t2.start();

			// creates a PrintWriter using the process output stream
			PrintWriter writer = new PrintWriter(process.getOutputStream());

			for (Beweiszeile z : beweis.gibZeilen()) {
				String coqCode = z.toCoqString();
				writer.write(coqCode);
				writer.write("\n");
				writer.flush();
			}
			writer.write("exit.");
			writer.write("\n");
			writer.flush();
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// Verarbeitung der Ergebnisse: out und err enthalten die
		// Gesamt-Ausgaben und die Fehler
		// "beweis" wird so angepasst, dass die jeweiligen Hypothesen und Ziele
		// an den entsprechenden
		// Beweisschritten hängen.

		try {
			while (t1.isAlive() && t2.isAlive())
				Thread.sleep(500);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!t2.isAlive())
			t1.interrupt();
		if (!t1.isAlive())
			t2.interrupt();

		if (err.size() > 0) {
			System.err.println(err.get(0));
			beweis.setError(err.get(0));
			return beweis;
		}

		if (out.get(out.size() - 1).startsWith("Error")) {
			System.err.println(out.get(out.size() - 1));
			beweis.setError(out.get(out.size() - 1));
			return beweis;
		}
		
		ArrayList<Hypothese> hypothesenFuerSchritt = new ArrayList<Hypothese>();
		ArrayList<Ziel> zieleFuerSchritt = new ArrayList<Ziel>();	
		
		int aktuellerSchritt = 0;
		int aktuellerOutput = 0;
		
		// bis zum ersten Theorem runter blättern
		
		while (!beweis.gibZeilen().get(aktuellerSchritt).toCoqString().startsWith("Theorem"))
		{
		    beweis.gibZeilen().get(aktuellerSchritt).setHypothesen(hypothesenFuerSchritt);
		    beweis.gibZeilen().get(aktuellerSchritt).setZiele(zieleFuerSchritt);
			aktuellerSchritt++;
		}

		

		
		while (aktuellerSchritt < beweis.gibZeilen().size()){
			// noch Output abzuarbeiten
			
			
			if (beweis.gibZeilen().get(aktuellerSchritt) instanceof BeweisZeileKommentar)
				{
				// Zeile ist Kommentar. An ihr hängen die zuletzt gefundenen Ziele
				// und Hypothesen.
				    beweis.gibZeilen().get(aktuellerSchritt).setHypothesen(hypothesenFuerSchritt);
				    beweis.gibZeilen().get(aktuellerSchritt).setZiele(zieleFuerSchritt);
				    aktuellerSchritt++;
				}
			else
			{
				// Zeile ist kein Kommentar. neue Hypothesen und Ziele aus dem Output lesen.
				hypothesenFuerSchritt = new ArrayList<Hypothese>();
				zieleFuerSchritt = new ArrayList<Ziel>();
			    beweis.gibZeilen().get(aktuellerSchritt).setHypothesen(hypothesenFuerSchritt);
			    beweis.gibZeilen().get(aktuellerSchritt).setZiele(zieleFuerSchritt);
				
				while (!out.get(aktuellerOutput).contains("subgoal")
						&& (aktuellerOutput < out.size()-1	))

					aktuellerOutput++;


				aktuellerOutput = aktuellerOutput + 1;
				while (aktuellerOutput < out.size()-1 && 
						!out.get(aktuellerOutput).contains("============================"))

				{
					if (!out.get(aktuellerOutput).trim().equals(""))
					{
						String[] hyp = out.get(aktuellerOutput).split(":");
						hypothesenFuerSchritt.add(new Hypothese(hyp[0].trim(),hyp[1].trim()));
					}
				   aktuellerOutput++;
				}
				aktuellerOutput++;	
				while (aktuellerOutput < out.size()-1 && !out.get(aktuellerOutput).contains(" subgoal"))
				{
					if (!out.get(aktuellerOutput).trim().equals("")
							&& !out.get(aktuellerOutput).startsWith("subgoal"))
					{
						zieleFuerSchritt.add(new Ziel(out.get(aktuellerOutput)));
					}
				   aktuellerOutput++;
				}
				
				if (aktuellerOutput < out.size()-1 && out.get(aktuellerOutput).contains("No more subgoals"))
				{
					// Bis zum nächsten Theorem alles ohne Ziele / Hypothesen
					aktuellerOutput++;
					aktuellerSchritt++;
					hypothesenFuerSchritt = new ArrayList<Hypothese>();
					zieleFuerSchritt = new ArrayList<Ziel>();	
					while (!beweis.gibZeilen().get(aktuellerSchritt).toCoqString().startsWith("Theorem")
						&& (aktuellerSchritt < beweis.gibZeilen().size()-1	))
					{
					    beweis.gibZeilen().get(aktuellerSchritt).setHypothesen(hypothesenFuerSchritt);
					    beweis.gibZeilen().get(aktuellerSchritt).setZiele(zieleFuerSchritt);
					    aktuellerSchritt++;
					}
					aktuellerSchritt--;
				}
				
			}
			aktuellerSchritt++;
		}
		

		return beweis;
	}



}