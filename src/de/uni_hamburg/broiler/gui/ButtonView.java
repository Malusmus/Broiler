package de.uni_hamburg.broiler.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import de.uni_hamburg.broiler.gui.button_style.*;
import de.uni_hamburg.broiler.logik.Beweis;
import de.uni_hamburg.broiler.logik.Taktik;
import de.uni_hamburg.broiler.logik.TaktikTyp;

/**
 * Klasse für die Erstellung der "ButtonView" bei der der Beweis
 * zusammengeklickt werden kann.
 * 
 * @author Britta
 *
 */
public class ButtonView extends View {

	private JPanel _left;
	private JPanel _middle;
	private JPanel _right;

	private BeweisPanel _beweisPanel;
	private HypothesenPanel _hypothesenPanel;
	private TaktikButtonPanel _hypothesenTaktikenPanel;
	private StatusPanel _statusPanel;
	private ZielPanel _zielPanel;
	private TaktikButtonPanel _zielTaktikenPanel;

	/**
	 * Konstruktor
	 * 
	 * @param taktiken
	 *            die Liste mit Taktiken
	 * @require taktiken != null
	 */
	public ButtonView(ArrayList<Taktik> taktiken, Beweis beweis) {
		super();

		_left = new JPanel();
		_left.setVisible(true);
		_middle = new JPanel();
		_middle.setVisible(true);
		_right = new JPanel();
		_right.setVisible(true);

		_beweisPanel = new BeweisPanel(beweis, this);
		_statusPanel = new StatusPanel();

		_hypothesenPanel = new HypothesenPanel(beweis, this);
		_zielPanel = new ZielPanel(beweis, this);

		ArrayList<Taktik> hypothesenTaktiken = gibTeilListe(taktiken, TaktikTyp.Hypothesis);

		_hypothesenTaktikenPanel = new TaktikButtonPanel(hypothesenTaktiken);

		ArrayList<Taktik> zieleTaktiken = gibTeilListe(taktiken, TaktikTyp.Variable);
		_zielTaktikenPanel = new TaktikButtonPanel(zieleTaktiken);

		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.X_AXIS));
		_left.setLayout(new BoxLayout(_left, BoxLayout.Y_AXIS));
		_middle.setLayout(new BoxLayout(_middle, BoxLayout.Y_AXIS));
		_right.setLayout(new BoxLayout(_right, BoxLayout.Y_AXIS));

		_left.add(_hypothesenPanel);
		_left.add(_hypothesenTaktikenPanel);
		_left.setBorder(BorderFactory.createDashedBorder(Color.pink));

		_middle.add(_beweisPanel);
		_middle.add(_toolPanel);
		_middle.add(_statusPanel);
		_middle.setBorder(BorderFactory.createDashedBorder(Color.red));

		_right.add(_zielPanel);
		_right.add(_zielTaktikenPanel);
		_right.setBorder(BorderFactory.createDashedBorder(Color.green));

		_mainPanel.add(_left);
		_mainPanel.add(_middle);
		_mainPanel.add(_right);

		_mainWindow.pack();
	}

	/*
	 * Hilfsfunktion die aus einer Liste aus gemischten Taktiken eine
	 * Sortenreine erstellt.
	 * 
	 * @param taktiken die ursprüngliche Liste
	 * 
	 * @param typ der Typ nach dem gefiltert wird
	 * 
	 * @require taktiken != null
	 * 
	 * @require typ.equals("Hypothese")||typ.equals("Ziel")
	 * 
	 * @ensure die zurückgegebene Liste enthält nur und alle Taktiken des
	 * gewünschten Typs
	 */
	private static ArrayList<Taktik> gibTeilListe(ArrayList<Taktik> taktiken, TaktikTyp typ) {
		assert taktiken != null : "Vorbedingung verletzt: taktiken != null";
		ArrayList<Taktik> neu = new ArrayList<>();
		for (Taktik t : taktiken) {
			if (t.gibTyp().equals(typ)) {
				neu.add(t);
			}
		}
		return neu;
	}

	public void changeBeweis(Beweis b) {
		_beweisPanel.setBeweis(b);
		_hypothesenPanel.setBeweis(b);
		_zielPanel.setBeweis(b);
	}

	public void beweisLineSelected(int zeile) {
		super.beweisLineSelected(zeile);
		_hypothesenPanel.showHypothesen(zeile);
		_zielPanel.showZiele(zeile);
	}

	@Override
	public String gibBeweis() {
		return	_beweisPanel.gibBeweis().gibCode();
	}
}
