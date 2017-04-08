package de.uni_hamburg.broiler.gui.button_style;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import de.uni_hamburg.broiler.gui.View;
import de.uni_hamburg.broiler.logik.Beweis;
import de.uni_hamburg.broiler.logik.BeweisZeileTaktik;
import de.uni_hamburg.broiler.logik.Beweiszeile;
import de.uni_hamburg.broiler.logik.Parameter;

/**
 * Das Panel in das der Beweis geschrieben wird, als Tabelle organisiert
 * @author Britta
 *
 */
public class BeweisPanel extends JPanel{
	
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
		DefaultTableModel model = (DefaultTableModel)_table.getModel();
		if (b == null)
			model.setRowCount(1);			
		else
			model.setRowCount(b.gibZeilen().size()+1);
		
		_beweis = b;
		int row=0;
		if (b!=null)
		for (Beweiszeile z: b.gibZeilen())
		{
			model.setValueAt(z.toTableString(),row,0);
			model.setValueAt("",row,1);
			model.setValueAt("",row,2);
			if (z instanceof BeweisZeileTaktik)
			{
				// parameter auswerten
				BeweisZeileTaktik zt = (BeweisZeileTaktik) z;
				if (zt.getParameter() != null && zt.getParameter().size() > 0)
				{
					int col = 0;
					for (Parameter p: zt.getParameter())
					{
						col++;
						model.setValueAt(p.gibCode(),row,col);
					}
				}
			}
			row++;
		}
		model.setValueAt("",row,0);
		adjustTableHeight();
		
	}
	
	public Beweis gibBeweis(){
		return _beweis;
	}
	
	public BeweisPanel(Beweis b, View v)
	{
		super(new BorderLayout());
		_parent = v;
		_beweis = b;
		_table = new JTable(1,3);
	
		_table.setPreferredSize(new Dimension(400,400));
		_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				
		DefaultTableModel myModel = new DefaultTableModel(new Object[]{"COMMAND","Parm 1","Parm 2"}, b.gibZeilen().size()+1) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		_table.setModel(myModel);
		_table.setRowHeight(20);
	

		_table.setFont(new Font("Courier New", Font.PLAIN, 20));
		
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		_table.getColumnModel().getColumn(0).setPreferredWidth(300);
		_table.getColumnModel().getColumn(1).setMaxWidth(32);
		_table.getColumnModel().getColumn(2).setMaxWidth(32);
		_table.setFillsViewportHeight(true);
		
		_table.setLayout(new BorderLayout());
		
		_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (_table.getSelectedRow() < _beweis.gibZeilen().size())
					_parent.beweisLineSelected(_table.getSelectedRow());
	        }
	    });
		
		JScrollPane scroller = new JScrollPane(_table);
		scroller.setPreferredSize(new Dimension(400,400));
	     scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	     scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller);
		
		setBeweis(b);

	}

}
