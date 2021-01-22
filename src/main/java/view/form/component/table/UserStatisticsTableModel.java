/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;

import domain.UserStatistics;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class UserStatisticsTableModel extends AbstractTableModel{

    private List<UserStatistics> userStats;
    private final String[] columnNames = new String[]{"ID", "Username", "System role", "Collection size",
        "Reviews posted", "Highest rated movie", "Score"};
    
    public UserStatisticsTableModel(List<UserStatistics> userStats) {
        this.userStats = userStats;
    }
    
    @Override
    public int getRowCount() {
        return userStats.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserStatistics stats = userStats.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return stats.getUser().getUserID();
            case 1: return stats.getUser().getUsername();
            case 2: 
                if (stats.getUser().isAdmin()) return "Admin";
                else return "User";
            case 3: return stats.getCollectionSize();
            case 4: return stats.getReviewCount();
            case 5: return stats.getHighestRatedMovie();
            case 6: 
                if (stats.getScore() == 0.0) return "";
                else return stats.getScore();
            default: return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    
}
