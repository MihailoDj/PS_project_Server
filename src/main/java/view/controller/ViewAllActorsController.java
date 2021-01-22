/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import controller.Controller;
import domain.Actor;
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
import javax.swing.table.TableColumnModel;
import util.Constants;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmViewActors;
import view.form.component.table.ActorTableModel;

/**
 *
 * @author Mihailo
 */
public class ViewAllActorsController {
    private final FrmViewActors frmViewActors;

    public ViewAllActorsController(FrmViewActors frmViewActors) {
        this.frmViewActors = frmViewActors;
        addActionListener();
        addListSelectionListener();
    }
    
    private void addListSelectionListener() {
        frmViewActors.getTableViewActorsAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmViewActors.getTblActors().getSelectedRow() != -1)
                    enableButtons();
                else
                    disableButtons();
            }
        });
    }
    
    private void addActionListener() {
        frmViewActors.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewActors.getTblActors().getSelectedRow();
                
                if (row != -1) {
                    Actor actor = ((ActorTableModel) frmViewActors.getTblActors().getModel()).getActorAt(row);
                    ServerFormCoordinator.getInstance().addParam(Constants.PARAM_ACTOR, actor);
                    ServerFormCoordinator.getInstance().openActorDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewActors, "You must select an actor", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewActors.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblActors();
            }
        });
        
        frmViewActors.getBtnAddActorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerFormCoordinator.getInstance().openActorForm();
            }
        });
        
        frmViewActors.getBtnDeleteActorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewActors.getTblActors().getSelectedRow();
                
                if (row != -1) {
                    try {
                        int check = JOptionPane.showConfirmDialog(frmViewActors, "Are you sure?", 
                                "Delete actor", JOptionPane.YES_NO_OPTION);
                        
                        if (check == JOptionPane.YES_OPTION) {
                            
                            Actor actor = ((ActorTableModel) frmViewActors.getTblActors().getModel()).getActorAt(row);
                            ServerFormCoordinator.getInstance().addParam(Constants.PARAM_ACTOR, null);
                            
                            Controller.getInstance().deleteActor(actor);
                            
                            JOptionPane.showMessageDialog(frmViewActors, "Actor successfully deleted", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(ViewAllActorsController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(frmViewActors, "Unable to delete actor", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewActors, "You must select an actor", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void openForm() {
        prepareView();
        frmViewActors.setLocationRelativeTo(ServerFormCoordinator.getInstance().getMainContoller().getFrmMain());
        frmViewActors.setVisible(true);
    }

    private void prepareView() {
        frmViewActors.setTitle("View actors");
    }

    private void fillTblActors() {
        List<Actor> actors;
        
        
        try {
            actors = Controller.getInstance().selectAllActors();
            ActorTableModel atm = new ActorTableModel(actors);
            frmViewActors.getTblActors().setModel(atm);
            
            setUpTableColumns();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewActors, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllActorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpTableColumns() throws Exception {
        TableColumnModel tcm = frmViewActors.getTblActors().getColumnModel();

        frmViewActors.getTblActors().setAutoCreateRowSorter(true);
        frmViewActors.getTblActors().getTableHeader().setResizingAllowed(false);

        frmViewActors.getTblActors().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
    
    public void enableButtons() {
        frmViewActors.getBtnDeleteActor().setEnabled(true);
        frmViewActors.getBtnDetails().setEnabled(true);
    }
    
    public void disableButtons() {
        frmViewActors.getBtnDeleteActor().setEnabled(false);
        frmViewActors.getBtnDetails().setEnabled(false);
    }
}
