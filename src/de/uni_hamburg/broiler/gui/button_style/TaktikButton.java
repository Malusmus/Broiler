package de.uni_hamburg.broiler.gui.button_style;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import de.uni_hamburg.broiler.logik.*;

/**
 * Spezieller Button zur Verwendung mit einer Taktik.
 * 
 * @author Britta
 *
 */
public class TaktikButton extends JButton {

	// TODO eigentlichen Kram einbauen.

	// Typ der Taktik
	Taktik _taktik;

	public TaktikButton(Taktik taktik) {
		super();
		setFont(new Font("Courier New", Font.PLAIN, 16));
		String name = taktik.gibLabel();
		name = name.replace("|", "<br/>");
		name = name.replace("_", "&nbsp;");
		name = "<html>" + name + "</html>";
		setText(name);
		this._taktik = taktik;
	}

	public Taktik gibTaktik() {
		return _taktik;
	}

}
