package de.uni_hamburg.broiler.gui;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.uni_hamburg.broiler.logik.*;

/**
 * Abstrakte View Klasse, handelt alle Bestandteile, die Programmübergreifend
 * notwendig sind.
 * 
 * @author Britta
 *
 */
public abstract class View extends Observable {

	// Hauptfenster
	protected JFrame _mainWindow;
	// Hauptframe
	protected JPanel _mainPanel;

	// Parameter allgemein
	protected int _breite = 1500;
	protected int _hoehe = 800;

	private JFileChooser _chooser;
	private String _currentFilepath = null;

	// Menuleiste
	private JMenuBar _menuBar;

	private JMenu _datei;
	private JMenuItem _dateiNeu;
	private JMenuItem _dateiOeffnen;
	private JMenuItem _dateiSpeichern;
	private JMenuItem _dateiSpeichernUnter;

	private JMenu _edit;
	private JMenu _view;
	private JMenu _navigation;
	private JMenu _tryTactics;
	private JMenu _templates;
	private JMenu _queries;
	private JMenu _tools;
	private JMenu _compile;
	private JMenu _windows;

	private JMenu _hilfe;
	private JMenuItem _beispiele;
	private JMenuItem _bedienung;
	private JMenuItem _tutorial;
	private JMenuItem _about;
	// Toolbar
	private JToolBar _toolBar;
	// FIXME Dummybutton
	JButton DUMMY;

	// ToolIcons WICHTIG! Müssen im gleichen Ordner wie diese Klasse liegen
	private ImageIcon _bearbeiteNaechstesElementIcon;
	private JButton _bearbeiteNaechstesElement;
	private JButton _bearbeiteNaechstesElement2;

	private ImageIcon _bearbeiteVorherigesElementIcon;
	private JButton _bearbeiteVorherigesElement;
	private JButton _bearbeiteVorherigesElement2;

	private ImageIcon _springeZurMarkiertenZeileIcon;
	private JButton _springeZurMarkiertenZeile;
	private JButton _springeZurMarkiertenZeile2;
	// ToDo Rest

	protected JPanel _toolPanel;

	/**
	 * Konstruktor, erstellt die Benutzeroberfläche
	 */
	public View() {

		_mainWindow = new JFrame();
		_mainWindow.getContentPane().setLayout(new BorderLayout());
		_mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_mainPanel = new JPanel();
		_mainPanel.setVisible(true);
		_mainWindow.add(_mainPanel);

		buildMenuBar();
		makeIcons();
		buildToolBar();
		buildToolPanel();

		_mainWindow.add(_menuBar);
		_mainPanel.setVisible(true);
		_mainWindow.getContentPane().add(_mainPanel);
		_mainWindow.setJMenuBar(_menuBar);
	}

	/*
	 * Baut das Menü auf.
	 */
	private void buildMenuBar() {
		// Komponenten erzeugen
		_menuBar = new JMenuBar();

		_datei = new JMenu("Datei");
		_dateiNeu = new JMenuItem("Neu");
		_dateiOeffnen = new JMenuItem("Öffnen");
		_dateiSpeichern = new JMenuItem("Speichern");
		_dateiSpeichernUnter = new JMenuItem("Speichern unter");

		_edit = new JMenu("Edit");
		_view = new JMenu("Ansicht");
		_navigation = new JMenu("Navigation");
		_tryTactics = new JMenu("Taktiken");
		_templates = new JMenu("Vorlagen");
		_queries = new JMenu("Queries");
		_tools = new JMenu("Werkzeuge");
		_compile = new JMenu("Kompilieren");
		_windows = new JMenu("Fenster");

		_hilfe = new JMenu("Hilfe");
		_beispiele = new JMenuItem("Beispiele");
		_bedienung = new JMenuItem("Bedienung");
		_tutorial = new JMenuItem("Tutorial");
		_about = new JMenuItem("About");

		_chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Coq Files", "v");
		_chooser.setFileFilter(filter);

		// Komponenten hinzufügen

		_menuBar.add(_datei);
		_datei.add(_dateiNeu);
		_datei.add(_dateiOeffnen);
		_datei.add(_dateiSpeichern);
		_datei.add(_dateiSpeichernUnter);

		_menuBar.add(_edit);
		_menuBar.add(_view);
		_menuBar.add(_navigation);
		_menuBar.add(_tryTactics);
		_menuBar.add(_templates);
		_menuBar.add(_queries);
		_menuBar.add(_tools);
		_menuBar.add(_compile);
		_menuBar.add(_windows);

		_menuBar.add(_hilfe);
		_hilfe.add(_beispiele);
		_hilfe.add(_bedienung);
		_hilfe.add(_tutorial);
		_hilfe.add(_about);

		addListeners();

		_menuBar.setVisible(true);
		_hilfe.setVisible(true);
		_about.setVisible(true);
		_datei.setVisible(true);

	}

	private void addListeners() {
		_dateiNeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers(new Notification("NEW", null));
			}

		});

		_dateiOeffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rueckgabeWert = _chooser.showOpenDialog(null);
				if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
					setChanged();
					_currentFilepath = _chooser.getSelectedFile().getAbsolutePath().toString();
					notifyObservers(new Notification("OPEN", _currentFilepath));
				}
			}
		});

		_dateiSpeichern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int rueckgabewert = _chooser.showSaveDialog(null);
				if (_currentFilepath == null) {
					if (rueckgabewert == JFileChooser.APPROVE_OPTION) {

						File file = _chooser.getSelectedFile();
						if (!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						try {
							FileWriter f = new FileWriter(file, false);
							f.write(gibBeweis());
							f.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				} else {
					try {
						FileWriter f = new FileWriter(new File(_currentFilepath), false);
						f.write(gibBeweis());
						f.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		_dateiSpeichernUnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int rueckgabewert = _chooser.showSaveDialog(null);

				if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
					File file = _chooser.getSelectedFile();
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					try {
						FileWriter f = new FileWriter(file, false);
						f.write(gibBeweis());
						f.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public void beweisLineSelected(int zeile) {
		setChanged();
		notifyObservers(new Notification("BEWEISZEILE", new Integer(zeile)));
	}

	/*
	 * Lädt ein Bild als Icon ein.
	 */
	protected ImageIcon createImageIcon(String iconName, String description) {

		File img;
		try {
			img = new File(getClass().getResource(iconName).toURI());
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(img);
				return new ImageIcon(bufferedImage);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Konnte File nicht finden");
				return null;
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			System.err.println("Uri war inkorrekt.");
			return null;
		}
	}

	/*
	 * Stellt die Buttons für die Toolbars bereit. Die ohne Zahl sind für die im
	 * Kopf, die mit der 2 für die Leiste im Arbeitsbereich.
	 */
	private void makeIcons() {
		_bearbeiteNaechstesElementIcon = createImageIcon("nextElem.png", "Bearbeite das nächste Element.");
		_bearbeiteNaechstesElement = new JButton(_bearbeiteNaechstesElementIcon);
		_bearbeiteNaechstesElement2 = new JButton(_bearbeiteNaechstesElementIcon);

		_bearbeiteVorherigesElementIcon = createImageIcon("prevElem.png", "Gehe zum vorherigen Element.");
		_bearbeiteVorherigesElement = new JButton(_bearbeiteVorherigesElementIcon);
		_bearbeiteVorherigesElement2 = new JButton(_bearbeiteVorherigesElementIcon);

		_springeZurMarkiertenZeileIcon = createImageIcon("jumpMark.png", "Springe zur markierten Zeile.");
		_springeZurMarkiertenZeile = new JButton(_springeZurMarkiertenZeileIcon);
		_springeZurMarkiertenZeile2 = new JButton(_springeZurMarkiertenZeileIcon);
	}

	/*
	 * Erstellt die Toolbar.
	 */
	private void buildToolBar() {
		_toolBar = new JToolBar("Tools");

		_toolBar.add(_bearbeiteNaechstesElement);
		_toolBar.add(_bearbeiteVorherigesElement);
		_toolBar.add(_springeZurMarkiertenZeile);

		// FIXME Dummybutton damit das Programm sich aktualisiert
		DUMMY = new JButton("Do stuff!");
		DUMMY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers();
			}
		});
		_toolBar.add(DUMMY);
		DUMMY.setVisible(true);

		_mainWindow.getContentPane().add(_toolBar, BorderLayout.NORTH);
	}

	/*
	 * Füllt das Toolpanel mit den Icons.
	 */
	private void buildToolPanel() {
		_toolPanel = new JPanel();
		_toolPanel.add(_bearbeiteNaechstesElement2);
		_toolPanel.add(_bearbeiteVorherigesElement2);
		_toolPanel.add(_springeZurMarkiertenZeile2);
	}

	public void run() {
		_mainWindow.setSize(_breite, _hoehe);
		_mainWindow.setVisible(true);
	}

	public abstract void changeBeweis(Beweis b);

	public abstract String gibBeweis();
}
