/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import controller.Controller;
import domain.Director;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.Constants;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmViewDirectors;
import view.form.component.table.DirectorTableModel;

/**
 *
 * @author Mihailo
 */
public class ViewAllDirectorsController {
    private final FrmViewDirectors frmViewDirectors;

    public ViewAllDirectorsController(FrmViewDirectors frmViewDirectors) {
        this.frmViewDirectors = frmViewDirectors;
        addActionListener();
        addListSelectionListener();
    }
    
    private void addListSelectionListener() {
        frmViewDirectors.getTableViewDirectorsAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmViewDirectors.getTblDirectors().getSelectedRow() != -1)
                    enableButtons();
                else
                    disableButtons();
            }
        });
    }
    
    private void addActionListener() {
        frmViewDirectors.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewDirectors.getTblDirectors().getSelectedRow();
                
                if (row != -1) {
                    Director director = ((DirectorTableModel) frmViewDirectors.getTblDirectors().getModel()).getDirectorAt(row);
                    ServerFormCoordinator.getInstance().addParam(Constants.PARAM_DIRECTOR, director);
                    ServerFormCoordinator.getInstance().openDirectorDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewDirectors, "You must select a director", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewDirectors.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblDirectors();
            }
        });
        
        frmViewDirectors.getBtnAddDirectorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerFormCoordinator.getInstance().openDirectorForm();
            }
        });
        
        frmViewDirectors.getBtnDeleteDirectorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewDirectors.getTblDirectors().getSelectedRow();
                
                if (row != -1) {
                    try {
                        int check = JOptionPane.showConfirmDialog(frmViewDirectors, "Are you sure?", 
                                "Delete director", JOptionPane.YES_NO_OPTION);
                        
                        if (check == JOptionPane.YES_OPTION) {
                            
                            Director director = ((DirectorTableModel) frmViewDirectors.getTblDirectors().getModel()).getDirectorAt(row);
                            ServerFormCoordinator.getInstance().addParam(Constants.PARAM_DIRECTOR, null);
                            
                            Controller.getInstance().deleteDirector(director);
                            
                            JOptionPane.showMessageDialog(frmViewDirectors, "Director successfully deleted", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(ViewAllDirectorsController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(frmViewDirectors, "Unable to delete director", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewDirectors, "You must select a director", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void openForm() {
        prepareView();
        frmViewDirectors.setLocationRelativeTo(ServerFormCoordinator.getInstance().getMainContoller().getFrmMain());
        frmViewDirectors.setVisible(true);
    }

    private void prepareView() {
        frmViewDirectors.setTitle("View directors");
    }

    private void fillTblDirectors() {
        List<Director> directors;
        
        
        try {
            directors = Controller.getInstance().selectAllDirectors();
            DirectorTableModel dtm = new DirectorTableModel(directors);
            frmViewDirectors.getTblDirectors().setModel(dtm);
            
            setUpTableColumns();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewDirectors, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllDirectorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpTableColumns() throws Exception {
        TableColumnModel tcm = frmViewDirectors.getTblDirectors().getColumnModel();
        
        TableColumn tcDateOfBirth = tcm.getColumn(3);
        tcDateOfBirth.setCellEditor(new DateTableEditor());
        tcDateOfBirth.setCellRenderer(new DateTableEditor());

        frmViewDirectors.getTblDirectors().setAutoCreateRowSorter(true);
        frmViewDirectors.getTblDirectors().getTableHeader().setResizingAllowed(false);

        frmViewDirectors.getTblDirectors().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
    
    public void enableButtons() {
        frmViewDirectors.getBtnDeleteDirector().setEnabled(true);
        frmViewDirectors.getBtnDetails().setEnabled(true);
    }
    
    public void disableButtons() {
        frmViewDirectors.getBtnDeleteDirector().setEnabled(false);
        frmViewDirectors.getBtnDetails().setEnabled(false);
    }
}
