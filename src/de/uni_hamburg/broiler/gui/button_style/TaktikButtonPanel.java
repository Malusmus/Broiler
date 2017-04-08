package de.uni_hamburg.broiler.gui.button_style;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.uni_hamburg.broiler.logik.Taktik;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class TaktikButtonPanel extends JPanel {

	public TaktikButtonPanel(ArrayList<Taktik> taktiken) {

		JPanel content = new JPanel(new GridLayout(0, 2, 10, 10));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(100, 500));
		content.setPreferredSize(new Dimension(100, taktiken.size() / 2 * 50));

		JScrollPane scroller = new JScrollPane(content);
		add(scroller, BorderLayout.CENTER);

		for (Taktik t : taktiken) {

			JPanel subPanel = new JPanel();
			subPanel.setLayout(new FlowLayout()); // (new
													// BoxLayout(subPanel,BoxLayout.Y_AXIS));
			Dimension d = new Dimension(150, 75);
			subPanel.setSize(d);
			subPanel.setPreferredSize(d);
			TaktikButton b = new TaktikButton(t);
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					((TaktikButton) arg0.getSource()).gibTaktik();
				}
			});
			b.setPreferredSize(d);
			b.setMinimumSize(d);
			JLabel l = new JLabel(t.gibBeschreibung());
			subPanel.add(l);
			subPanel.add(b);
			content.add(subPanel);

			scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setPreferredSize(new Dimension(300, 500));
		}
	}

}
