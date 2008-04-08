/*
 *    ALMA - Atacama Large Millimiter Array
 *    (c) European Southern Observatory, 2002
 *    Copyright by ESO (in the framework of the ALMA collaboration)
 *    and Cosylab 2002, All rights reserved
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, 
 *    MA 02111-1307  USA
 */
package alma.acs.gui.loglevel.leveldlg;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.cosylab.logging.engine.log.LogTypeHelper;
import com.cosylab.logging.settings.LogTypeRenderer;
import com.cosylab.logging.client.EntryTypeIcon;

/**
 * The table showing the log levels
 * 
 * @author acaproni
 *
 */
public class LogLevelTable extends JTable {
	
	// The renderer for the global and local log types
	private LogTypeCellRenderer renderer = new LogTypeCellRenderer();
	
	// The editor for log levels
	private JComboBox editor;
	public LogTypeRenderer editorRenderer= new LogTypeRenderer();
	
	// The identifiers of the cols
	private static final Integer NAMEDLOGGER_ID=new Integer(0);
	private static final Integer DEFAULT_ID=new Integer(1);
	private static final Integer LOCAL_ID=new Integer(2);
	private static final Integer GLOBAL_ID=new Integer(3);
	private static final Object[] COL_IDS = {
		NAMEDLOGGER_ID,
		DEFAULT_ID,
		LOCAL_ID,
		GLOBAL_ID
	};
	
	/**
	 * Constructor
	 * 
	 * @param model The model of this table
	 */
	public LogLevelTable(LogLevelModel model) {
		super();
		if (model==null) {
			throw new IllegalArgumentException("Invalid null table model in constructor");
		}
		
		String[] descs = new String[LogTypeHelper.values().length];
		for (int t=0; t<LogTypeHelper.values().length; t++) {
			descs[t]=LogTypeHelper.values()[t].logEntryType;
		}
		editor=new JComboBox(descs);
		
		setModel(model);
		
		// Set the row sorter
		setAutoCreateRowSorter(true);
		List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(0,SortOrder.ASCENDING));
		getRowSorter().setSortKeys(sortKeys);
		
		editor.setSelectedIndex(0);
		editor.setEditable(false);
		editor.setMaximumRowCount(LogTypeHelper.values().length);
		editor.setRenderer(editorRenderer);
		
		columnModel.getColumn(2).setCellEditor(new DefaultCellEditor(editor));
		columnModel.getColumn(3).setCellEditor(new DefaultCellEditor(editor));
		for (int t=0; t<4; t++) {
			columnModel.getColumn(t).setMinWidth(50);
			columnModel.getColumn(t).setIdentifier(LogLevelTable.COL_IDS[t]);
		}
		
		setRowMargin(2);
		setRowHeight(EntryTypeIcon.getIconsVSize()+5+getRowMargin());
		getColumn(LogLevelTable.DEFAULT_ID).sizeWidthToFit();
		
		setRowSelectionAllowed(false);
		
	}
	
	/**
	 * Return the renderer for a given cell
	 */
	public TableCellRenderer getCellRenderer(int row, int column) {
		if (column==2 || column==3) {
			return renderer;
		}
		return super.getCellRenderer(row, column);
	}
	
	/**
	 * Return the editor for a given cell
	 */
	public TableCellEditor getCellEditor(int row, int column) {
		if (column==2 || column==3) {
			Object val=getModel().getValueAt(row, column);
			System.out.println("Value="+val.toString());
			DefaultCellEditor edt = (DefaultCellEditor)super.getCellEditor(row, column);
			JComboBox edtCB = (JComboBox)edt.getComponent();
			edtCB.setSelectedIndex(Integer.parseInt(val.toString()));
			return edt;
		}
		return super.getCellEditor(row, column);
	}
	
	/**
	 * Set the tootip for the name of the logger
	 */
	public String getToolTipText(MouseEvent e) {
		String tip=null;
		Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
        int realRowIndex=convertRowIndexToModel(rowIndex);
        if (realColumnIndex==0) {
        	tip=getModel().getValueAt(realRowIndex, 0).toString();
        }
		
		return tip;
	}
	
}
