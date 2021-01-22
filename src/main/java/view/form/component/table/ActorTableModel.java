/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;

import domain.Actor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class ActorTableModel extends AbstractTableModel{
    private final List<Actor> actors;
    private final String[] columnNames = {
        "ID", 
        "First name", 
        "Last name", 
        "Biography"
    };
    private final Class[] columnClasses = {
        Integer.class,
        String.class,
        String.class,
        String.class
    };
    
    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public int getRowCount() {
        if (actors == null) 
            return 0;
         else 
            return actors.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Actor actor = actors.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return actor.getActorID();
            case 1: return actor.getFirstName();
            case 2: return actor.getLastName();
            case 3: return actor.getBiography();
            default: return "N/A";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Actor actor = actors.get(rowIndex);
        
        try{
            switch(columnIndex) {
                case 1:
                    actor.setFirstName(String.valueOf(value));
                    break;
                case 2:
                    actor.setLastName(String.valueOf(value));
                    break;
                case 3:
                    actor.setBiography(String.valueOf(value));
                    break;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ActorTableModel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public Actor getActorAt(int row) {
        return actors.get(row);
    }
}
