package de.uni_hamburg.broiler.schnittstelle.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StreamGobbler implements Runnable {
    private InputStream is;
    private ArrayList<String> target;
    private char type; // "E" for ERROR, "O" for output

    public StreamGobbler( InputStream is, ArrayList<String> target, char type) {
        this.is = is;
        this.target = target;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader( is );
            BufferedReader br = new BufferedReader( isr );
            String line = null;
            while ( ( line = br.readLine() ) != null ) {
				if (type == 'E')
				{
					if (!line.trim().equals("") && !line.trim().endsWith(" <"))
					{
						target.add(line);
						break;
					}
				}
				if (type == 'O')
				{
						target.add(line);
						if (line.contains("exit"))
							break;
						if (line.startsWith("Error:"))
							break;
				}					
            }
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
    }
}