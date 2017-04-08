package de.uni_hamburg.broiler;

import static org.junit.Assert.*;

import org.junit.Test;

/** 
 * Diesen Text habe ich aus meinen SE2 Unterlagen �bernommen; 
 * damit kann man �berpr�fen ob die asserts aktiviert sind.
 *
 */
public class AssertTest {

	@Test
	public void assertionEnabledTest()
    {
        boolean assertsEnabled = false;
        assert assertsEnabled = true;

        if (!assertsEnabled)
        {
            fail("Asserts muessen aktiviert sein -ea");
            /*
             * Anleitung f�r die Aktivierung von asserts in Eclipse:
             * 
             * Window > Preferences > Java > Installed JREs -> ausgewaehlte JRE
             * markieren und auf "Edit" klicken > im erscheinenden Dialog bei
             * "Default VM Arguments" -ea eingeben
             */
        }
    }
}