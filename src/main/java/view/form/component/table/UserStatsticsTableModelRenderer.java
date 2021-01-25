/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Mihailo
 */
public class UserStatsticsTableModelRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (table.getValueAt(row, column).equals("online")) {
                setText("ONLINE");
                setForeground(Color.green.darker());
            } else if (table.getValueAt(row, column).equals("offline")){
                setText("OFFLINE");
                setForeground(Color.red);
            }
            return this;
    }
    
    
}
