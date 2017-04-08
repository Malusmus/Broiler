package de.uni_hamburg.broiler.gui.button_style;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Das Panel für die Darstellung der Statusnachrichten, Messages, Errors und
 * Jobs. Hält als vierten Tab die Roadmap.
 * 
 * @author Britta
 *
 */
public class StatusPanel extends JTabbedPane {

	private JPanel _messages = new JPanel();
	private JPanel _errors = new JPanel();
	private JPanel _jobs = new JPanel();
	private JPanel _map = new JPanel();
	private String _messagesHeader = StatusTab.Messages.toString();
	private String _errorsHeader = StatusTab.Errors.toString();
	private String _jobsHeader = StatusTab.Jobs.toString();
	private String _mapHeader = StatusTab.Roadmap.toString();
	private Roadmap _roadmap;

	public StatusPanel() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(300,300));

		buildStatusPanel();
		this.setVisible(true);

		// FIXME Roadmap füllen
		_roadmap = new Roadmap();
	}

	/*
	 * Erstellt das StatusPanel mit den 3 Tabs Messages, Errors und Jobs.
	 */
	private void buildStatusPanel() {
		_messages = new JPanel();
		_errors = new JPanel();
		_jobs = new JPanel();
		_map = new JPanel();

		_messages.add(new JLabel(
				"Erstelle einen Beweis indem du die gewünschten Elemente im oben stehenden Fenster kombinierst."));
		_errors.add(new JLabel("Noch kein Fehler."));
		_jobs.add(new JLabel("Noch keine Jobs."));
		_map.add(new JLabel("Coq wurde noch nicht ausgeführt."));

		_messages.setVisible(true);
		_errors.setVisible(true);
		_jobs.setVisible(true);
		_map.setVisible(true);

		this.addTab(_messagesHeader, _messages);
		this.addTab(_errorsHeader, _errors);
		this.addTab(_jobsHeader, _jobs);
		this.addTab(_mapHeader, _map);
	}

	/**
	 * Updated die Tabs.
	 * 
	 * @param update
	 *            der neue Text
	 * 
	 * @require update.length == 3
	 */
	public void updateTabs(String[] update) {
		assert update.length == 3 : "Vorbedingung verletzt: update.length == 3";
		_messages.add(new JLabel(update[0]));
		_errors.add(new JLabel(update[1]));
		_jobs.add(new JLabel(update[2]));
		_map.removeAll();
		_map.add(new JLabel(_roadmap.erzeugeMap()));
	}

}
