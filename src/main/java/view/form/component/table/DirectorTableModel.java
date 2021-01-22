/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;
import domain.Director;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class DirectorTableModel extends AbstractTableModel{

    private final List<Director> directors;
    private final String[] columnNames = {
        "ID", 
        "First name", 
        "Last name", 
        "Date of birth"
    };
    private final Class[] columnClasses = {
        Integer.class,
        String.class,
        String.class,
        LocalDate.class
    };
    
    public DirectorTableModel(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public int getRowCount() {
        if (directors == null) 
            return 0;
         else 
            return directors.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Director director = directors.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return director.getDirectorID();
            case 1: return director.getFirstName();
            case 2: return director.getLastName();
            case 3: return director.getDateOfBirth();
            default: return "N/A";
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Director director = directors.get(rowIndex);
        
        try{
            switch(columnIndex) {
                case 1:
                    director.setFirstName(String.valueOf(value));
                    break;
                case 2:
                    director.setLastName(String.valueOf(value));
                    break;
                case 3:
                    director.setDateOfBirth((LocalDate)value);
                    break;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(DirectorTableModel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Director getDirectorAt(int row) {
        return directors.get(row);
    }
    
}
