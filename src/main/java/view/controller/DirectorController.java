/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import controller.Controller;
import domain.Director;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Constants;
import util.FormMode;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmDirector;

/**
 *
 * @author Mihailo
 */
public class DirectorController {
    private final FrmDirector frmDirector;
    
    public DirectorController(FrmDirector frmDirector) {
        this.frmDirector = frmDirector;
        addActionListener();
    }
    
    private void addActionListener() {
        frmDirector.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                    
                    Director director = new Director();
                    director.setFirstName(frmDirector.getTxtDirectorFirstName().getText().trim());
                    director.setLastName(frmDirector.getTxtDirectorLastName().getText().trim());
                    director.setDateOfBirth(frmDirector.getDateOfBirth().getDate());
                    
                    Controller.getInstance().insertDirector(director);
                    
                    JOptionPane.showMessageDialog(frmDirector, "Director successfully saved!");
                    frmDirector.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmDirector.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmDirector, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmDirector.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpComponents(FormMode.FORM_EDIT);
            }
        });

        frmDirector.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmDirector.dispose();
            }
        });

        frmDirector.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmDirector, "Are you sure?", 
                        "Delete movie", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    Director director = makeDirectorFromForm();
                    try {
                        Controller.getInstance().deleteDirector(director);
                        
                        JOptionPane.showMessageDialog(frmDirector, "Director deleted successfully!\n", "Delete director", JOptionPane.INFORMATION_MESSAGE);
                        frmDirector.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmDirector, ex.getMessage(), "Delete director", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmDirector.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    int check = JOptionPane.showConfirmDialog(frmDirector, "Are you sure?", 
                        "Update director", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Director director = makeDirectorFromForm();
                       Controller.getInstance().updateDirector(director);
                        
                        JOptionPane.showMessageDialog(frmDirector, "Director updated successfully!\n", "Update director", JOptionPane.INFORMATION_MESSAGE);
                        frmDirector.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(DirectorController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmDirector, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
    }
    
    public void openForm(FormMode formMode) {
        frmDirector.setTitle("Director form");
        setUpComponents(formMode);
        frmDirector.setLocationRelativeTo(ServerFormCoordinator.getInstance().getMainContoller().getFrmMain());
        frmDirector.setVisible(true);
    }

    private void setUpComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmDirector.getTxtDirectorID().setText("auto");
                
                frmDirector.getBtnCancel().setEnabled(true);
                frmDirector.getBtnDelete().setEnabled(false);
                frmDirector.getBtnUpdate().setEnabled(false);
                frmDirector.getBtnEnableChanges().setEnabled(false);
                frmDirector.getBtnAdd().setEnabled(true);

                frmDirector.getTxtDirectorID().setEditable(false);
                frmDirector.getTxtDirectorFirstName().setEditable(true);
                frmDirector.getTxtDirectorLastName().setEditable(true);
                frmDirector.getDateOfBirth().setEnabled(true);
                break;
            case FORM_VIEW:
                frmDirector.getBtnCancel().setEnabled(true);
                frmDirector.getBtnDelete().setEnabled(true);
                frmDirector.getBtnUpdate().setEnabled(false);
                frmDirector.getBtnEnableChanges().setEnabled(true);
                frmDirector.getBtnAdd().setEnabled(false);
                
                frmDirector.getTxtDirectorID().setEditable(false);
                frmDirector.getTxtDirectorFirstName().setEditable(false);
                frmDirector.getTxtDirectorLastName().setEditable(false);
                frmDirector.getDateOfBirth().setEnabled(false);

                Director director = (Director) ServerFormCoordinator.getInstance().getParam(Constants.PARAM_DIRECTOR);
                frmDirector.getTxtDirectorID().setText(director.getDirectorID() + "");
                frmDirector.getTxtDirectorFirstName().setText(director.getFirstName());
                frmDirector.getTxtDirectorLastName().setText(director.getLastName());
                frmDirector.getDateOfBirth().setDate(director.getDateOfBirth());
                break;
            case FORM_EDIT:
                frmDirector.getBtnCancel().setEnabled(true);
                frmDirector.getBtnDelete().setEnabled(false);
                frmDirector.getBtnUpdate().setEnabled(true);
                frmDirector.getBtnEnableChanges().setEnabled(false);
                frmDirector.getBtnAdd().setEnabled(false);

                frmDirector.getTxtDirectorID().setEditable(false);
                frmDirector.getTxtDirectorFirstName().setEditable(true);
                frmDirector.getTxtDirectorLastName().setEditable(true);
                frmDirector.getDateOfBirth().setEnabled(true);
                break;
        }
    }
    
    private void validateForm() throws Exception{
        if (frmDirector.getTxtDirectorFirstName().getText().trim().isEmpty() || frmDirector.getTxtDirectorFirstName() == null 
                || frmDirector.getDateOfBirth().getDate() == null
                || frmDirector.getTxtDirectorLastName().getText().trim().isEmpty() 
                || frmDirector.getTxtDirectorLastName() == null) {
            throw new Exception("Invalid input");
        }
    }
    
    private Director makeDirectorFromForm() {
        Director director = new Director();
        director.setDirectorID(Long.parseLong(frmDirector.getTxtDirectorID().getText().trim()));
        director.setFirstName(frmDirector.getTxtDirectorFirstName().getText().trim());
        director.setLastName(frmDirector.getTxtDirectorLastName().getText().trim());
        director.setDateOfBirth(frmDirector.getDateOfBirth().getDate());
        
        return director;
    }
}
