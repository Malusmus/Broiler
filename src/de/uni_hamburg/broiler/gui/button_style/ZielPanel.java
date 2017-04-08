package de.uni_hamburg.broiler.gui.button_style;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import de.uni_hamburg.broiler.gui.View;
import de.uni_hamburg.broiler.logik.Beweis;
import de.uni_hamburg.broiler.logik.BeweisZeileTaktik;
import de.uni_hamburg.broiler.logik.Beweiszeile;
import de.uni_hamburg.broiler.logik.Hypothese;
import de.uni_hamburg.broiler.logik.Parameter;
import de.uni_hamburg.broiler.logik.Ziel;

/**
 * Das Panel in dem die aktuellen Hypothesen gelistet werden.
 * @author Britta
 *
 */
public class ZielPanel extends JPanel{
	JTable _table;
	Beweis _beweis;
	View _parent;
	
	private void adjustTableHeight()
	{
		_table.setPreferredSize(new Dimension((int) _table.getPreferredSize().getWidth(),
				_table.getRowHeight() * (_beweis.gibZeilen().size()+1)));
	}
	
	public void setBeweis(Beweis b)
	{
		_beweis = b;
	}
	
	public void showZiele(int zeile)
	{
		DefaultTableModel model = (DefaultTableModel)_table.getModel();
		if (_beweis == null)
			model.setRowCount(0);			
		else
		{
			ArrayList<Ziel> ziel = _beweis.gibZeilen().get(zeile).getZiele();

			if (ziel == null)
				model.setRowCount(0);
			else
			{
				model.setRowCount(ziel.size());
				for (int i=0; i<ziel.size(); i++)
				{
					model.setValueAt("<html>"+ziel.get(i).gibZiel()+"</html>",i,0);
				}
			}
		}
	}
		
	public ZielPanel(Beweis b, View v)
	{
		super(new BorderLayout());
		_beweis = b;
		_table = new JTable(2,0);
		_parent = v;
	
		_table.setPreferredSize(new Dimension(100,100));
		_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
		DefaultTableModel myModel = new DefaultTableModel(new Object[]{"ZIEL"}, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		_table.setModel(myModel);
		_table.setRowHeight(20);
	

		_table.setFont(new Font("Courier New", Font.PLAIN, 20));
		
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		_table.setFillsViewportHeight(true);
		
		_table.setLayout(new BorderLayout());
		
		JScrollPane scroller = new JScrollPane(_table);
		scroller.setPreferredSize(new Dimension(100,100));
	     scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	     scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller);
		
		setBeweis(b);

	}
}
