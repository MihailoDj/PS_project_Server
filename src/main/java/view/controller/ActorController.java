/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import controller.Controller;
import domain.Actor;
import util.FormMode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Constants;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmActor;

/**
 *
 * @author Mihailo
 */
public class ActorController {
    private final FrmActor frmActor;
    
    public ActorController(FrmActor frmActor) {
        this.frmActor = frmActor;
        addActionListener();
        addKeyListener();
    }
    
    private void addKeyListener() {
        frmActor.txtBiographyKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int descriptionLength = frmActor.getBiography().getText().length();
                int charsRemaining = 150 - descriptionLength;
                frmActor.getLblCharactersRemaining().setText("Characters remaining: " + charsRemaining);
            }
        });
    }
    
    private void addActionListener() {
        frmActor.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                   
                    Actor actor = new Actor();
                    actor.setFirstName(frmActor.getTxtActorFirstName().getText().trim());
                    actor.setLastName(frmActor.getTxtActorLastName().getText().trim());
                    actor.setBiography(frmActor.getBiography().getText().trim());
                    
                    Controller.getInstance().insertActor(actor);
                            
                    JOptionPane.showMessageDialog(frmActor, "Actor successfully saved!");
                    frmActor.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmActor.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmActor.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpComponents(FormMode.FORM_EDIT);
            }
        });

        frmActor.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmActor.dispose();
            }
        });

        frmActor.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmActor, "Are you sure?", 
                        "Delete actor", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    Actor actor = makeActorFromForm();
                    try {
                        Controller.getInstance().deleteActor(actor);
                        
                        JOptionPane.showMessageDialog(frmActor, "Actor deleted successfully!\n", 
                                "Delete actor", JOptionPane.INFORMATION_MESSAGE);
                        frmActor.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Delete actor", 
                                JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(ActorController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmActor.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    
                    int check = JOptionPane.showConfirmDialog(frmActor, "Are you sure?", 
                        "Update actor", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Actor actor = makeActorFromForm();
                        Controller.getInstance().updateActor(actor);
                        
                        JOptionPane.showMessageDialog(frmActor, "Actor updated successfully!\n", 
                                "Update actor", JOptionPane.INFORMATION_MESSAGE);
                        frmActor.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ActorController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmActor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
    }
    
    public void openForm(FormMode formMode) {
        frmActor.setTitle("Actor form");
        setUpComponents(formMode);
        frmActor.setLocationRelativeTo(ServerFormCoordinator.getInstance().getMainContoller().getFrmMain());
        frmActor.setVisible(true);
    }

    private void setUpComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmActor.getTxtActorID().setText("auto");
                frmActor.getLblCharactersRemaining().setText("Characters remaining: 150");
                
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(false);
                frmActor.getBtnUpdate().setEnabled(false);
                frmActor.getBtnEnableChanges().setEnabled(false);
                frmActor.getBtnAdd().setEnabled(true);

                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(true);
                frmActor.getTxtActorLastName().setEditable(true);
                frmActor.getBiography().setEditable(true);
                break;
            case FORM_VIEW:
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(true);
                frmActor.getBtnUpdate().setEnabled(false);
                frmActor.getBtnEnableChanges().setEnabled(true);
                frmActor.getBtnAdd().setEnabled(false);
                
                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(false);
                frmActor.getTxtActorLastName().setEditable(false);
                frmActor.getBiography().setEditable(false);

                Actor actor = (Actor) ServerFormCoordinator.getInstance().getParam(Constants.PARAM_ACTOR);
                frmActor.getTxtActorID().setText(actor.getActorID() + "");
                frmActor.getTxtActorFirstName().setText(actor.getFirstName());
                frmActor.getTxtActorLastName().setText(actor.getLastName());
                frmActor.getBiography().setText(actor.getBiography());
                frmActor.getLblCharactersRemaining().setText("Characters remaining: " + (150 - actor.getBiography().length()));
                break;
            case FORM_EDIT:
                frmActor.getBtnCancel().setEnabled(true);
                frmActor.getBtnDelete().setEnabled(false);
                frmActor.getBtnUpdate().setEnabled(true);
                frmActor.getBtnEnableChanges().setEnabled(false);
                frmActor.getBtnAdd().setEnabled(false);

                frmActor.getTxtActorID().setEditable(false);
                frmActor.getTxtActorFirstName().setEditable(true);
                frmActor.getTxtActorLastName().setEditable(true);
                frmActor.getBiography().setEditable(true);
                break;
        }
    }
    
    private void validateForm() throws Exception{
        if (frmActor.getTxtActorFirstName().getText().trim().isEmpty() || frmActor.getTxtActorFirstName() == null
                || frmActor.getTxtActorLastName().getText().trim().isEmpty() 
                || frmActor.getTxtActorLastName() == null) {
            throw new Exception("Invalid input");
        }
    }
    
    private Actor makeActorFromForm() {
        Actor actor = new Actor();
        actor.setActorID(Long.parseLong(frmActor.getTxtActorID().getText().trim()));
        actor.setFirstName(frmActor.getTxtActorFirstName().getText().trim());
        actor.setLastName(frmActor.getTxtActorLastName().getText().trim());
        actor.setBiography(frmActor.getBiography().getText().trim());
                
        return actor;
    }
}
